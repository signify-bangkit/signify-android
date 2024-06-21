package com.signify.app.presentation.fragment.analyze.analyze

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.DialogCnnDisclaimerBinding
import com.signify.app.databinding.DialogLogoutBinding
import com.signify.app.databinding.FragmentAnalyzeBinding
import com.signify.app.presentation.fragment.analyze.GestureRecognizerHelper
import com.signify.app.utils.showToast
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class AnalyzeFragment : BaseFragment<FragmentAnalyzeBinding>(),
    GestureRecognizerHelper.GestureRecognizerListener {

    companion object {
        private const val TAG = "Hand gesture recognizer"
    }

    private lateinit var gestureRecognizerHelper: GestureRecognizerHelper
    private val viewModel: AnalyzeViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private var currentLetter: String? = null
    private var letterStartTime: Long = 0
    private val scanDuration: Long = 1500

    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var cameraFacing = CameraSelector.LENS_FACING_FRONT

    /** Blocking ML operations are performed using this executor */
    private lateinit var backgroundExecutor: ExecutorService

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    context,
                    "Permission request granted",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Permission request denied",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun beforeSomething() {
        super.beforeSomething()

        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 400
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 400
        }

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    override fun onResume() {
        super.onResume()
        // Start the GestureRecognizerHelper again when users come back
        // to the foreground.
        backgroundExecutor.execute {
            if (gestureRecognizerHelper.isClosed()) {
                gestureRecognizerHelper.setupGestureRecognizer()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (this::gestureRecognizerHelper.isInitialized) {
            viewModel.setMinHandDetectionConfidence(gestureRecognizerHelper.minHandDetectionConfidence)
            viewModel.setMinHandTrackingConfidence(gestureRecognizerHelper.minHandTrackingConfidence)
            viewModel.setMinHandPresenceConfidence(gestureRecognizerHelper.minHandPresenceConfidence)
            viewModel.setDelegate(gestureRecognizerHelper.currentDelegate)

            // Close the Gesture Recognizer helper and release resources
            backgroundExecutor.execute { gestureRecognizerHelper.clearGestureRecognizer() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Shut down our background executor
        backgroundExecutor.shutdown()
        backgroundExecutor.awaitTermination(
            Long.MAX_VALUE, TimeUnit.NANOSECONDS
        )
    }

    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAnalyzeBinding {
        return FragmentAnalyzeBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()

        initListener()

        // Initialize our background executor
        backgroundExecutor = Executors.newSingleThreadExecutor()

        // Wait for the views to be properly laid out
        binding.viewFinder.post {
            // Set up the camera and its use cases
            setUpCamera()
        }

        // Create the Hand Gesture Recognition Helper that will handle the
        // inference
        backgroundExecutor.execute {
            gestureRecognizerHelper = GestureRecognizerHelper(
                context = requireContext(),
                runningMode = RunningMode.LIVE_STREAM,
                minHandDetectionConfidence = viewModel.currentMinHandDetectionConfidence,
                minHandTrackingConfidence = viewModel.currentMinHandTrackingConfidence,
                minHandPresenceConfidence = viewModel.currentMinHandPresenceConfidence,
                currentDelegate = viewModel.currentDelegate,
                gestureRecognizerListener = this
            )
        }
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        binding.imgSwitchCamera.setOnClickListener {
            switchCamera()
        }

        binding.btnSave.setOnClickListener {
            val appended = binding.edResults.text
            if (appended.isNotEmpty()) {
                val extras = FragmentNavigatorExtras(
                    binding.contentLayoutText to "content_layout_shared",
                    binding.btnSave to "btn_save",
                )
                val direction =
                    AnalyzeFragmentDirections.actionAnalyzeFragmentToOutputFragment(
                        output = appended.toString()
                    )
                findNavController().navigate(direction, extras)
            } else {
                showToast(requireActivity(), "Result's Empty!")
            }
        }

        binding.btnBackspace.setOnClickListener {
            val start = binding.edResults.text.length
            if (start > 0) {
                binding.edResults.text.delete(start - 1, start)
            }
        }

        binding.btnSpace.setOnClickListener {
            val start = binding.edResults.text.length
            binding.edResults.text.insert(start, " ")
        }

        binding.topBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.switchCNN -> {
                    showDisclaimerDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun setUpCamera() {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(
            {
                cameraProvider = cameraProviderFuture.get()

                bindCameraUseCases()
            }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun switchCamera() {
        cameraFacing = if (cameraFacing == CameraSelector.LENS_FACING_FRONT) {
            CameraSelector.LENS_FACING_BACK
        } else {
            CameraSelector.LENS_FACING_FRONT
        }

        binding.overlay.updateCameraFrontFacing(cameraFacing == CameraSelector.LENS_FACING_FRONT)

        bindCameraUseCases()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun bindCameraUseCases() {

        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(cameraFacing).build()

        preview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(binding.viewFinder.display.rotation)
            .build()

        imageAnalyzer =
            ImageAnalysis.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(binding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(backgroundExecutor) { image ->
                        recognizeHand(image)
                    }
                }

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageAnalyzer
            )

            preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }


    private fun recognizeHand(imageProxy: ImageProxy) {
        gestureRecognizerHelper.recognizeLiveStream(
            imageProxy = imageProxy,
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation =
            binding.viewFinder.display.rotation
    }

    override fun onResults(
        resultBundle: GestureRecognizerHelper.ResultBundle
    ) {
        activity?.runOnUiThread {
            val gestureCategories = resultBundle.results.first().gestures()
            with(binding) {
                if (gestureCategories.isNotEmpty()) {
                    val gesture = gestureCategories.first()
                        .sortedByDescending { it.score() }
                    val score = gesture.first().score()
                    val category = gesture.first().categoryName().toString()

                    Log.d("GESTURE", "onResults: $gesture")

                    scoreText.text = String.format(Locale.US, "%.2f", score)
                    categoryText.text = category

                    appendCategory(category)

                } else {
                    resetTimer()
                    scoreText.text = "--"
                    categoryText.text = "--"
                }
            }

            binding.overlay.setResults(
                resultBundle.results.first(),
                resultBundle.inputImageHeight,
                resultBundle.inputImageWidth,
                RunningMode.LIVE_STREAM
            )

            binding.overlay.invalidate()
        }
    }

    private fun appendCategory(letter: String?) {
        if (letter == null || !letter.matches(Regex("[a-zA-Z0-9]"))) {
            resetTimer()
            return
        }

        if (letter == currentLetter) {
            if (System.currentTimeMillis() - letterStartTime >= scanDuration) {
                binding.edResults.append(letter)
                resetTimer()
            }
        } else {
            resetTimer()
            currentLetter = letter
            letterStartTime = System.currentTimeMillis()
            handler.postDelayed({
                if (currentLetter == letter) {
                    binding.edResults.append(letter)
                    resetTimer()
                }
            }, scanDuration)
        }
    }

    private fun resetTimer() {
        currentLetter = null
        letterStartTime = 0
        handler.removeCallbacksAndMessages(null)
    }

    override fun onError(error: String, errorCode: Int) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDisclaimerDialog() {
        val dbinding =
            DialogCnnDisclaimerBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = MaterialAlertDialogBuilder(
            requireContext(),
            R.style.DialogAnimation
        )
            .setView(dbinding.root)
            .setCancelable(true)
            .create()

        dialog.show()

        dbinding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dbinding.btnYes.setOnClickListener {
            dialog.dismiss()
            val extras = FragmentNavigatorExtras(
                binding.toolbarTitle to "title_app",
                binding.btnSave to "btn_save"
            )
            findNavController().navigate(
                AnalyzeFragmentDirections.actionAnalyzeFragmentToAnalyzeCNNFragment(),
                extras
            )
        }
    }
}