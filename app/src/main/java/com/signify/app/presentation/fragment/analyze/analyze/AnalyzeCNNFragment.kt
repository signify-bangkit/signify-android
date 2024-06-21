package com.signify.app.presentation.fragment.analyze.analyze

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentAnalyzeCNNBinding
import com.signify.app.utils.ImageClassifierHelper
import com.signify.app.utils.showToast
import java.util.concurrent.Executors

class AnalyzeCNNFragment : BaseFragment<FragmentAnalyzeCNNBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAnalyzeCNNBinding {
        return FragmentAnalyzeCNNBinding.inflate(inflater, container, false)
    }

    private var cameraSelector: CameraSelector =
        CameraSelector.DEFAULT_BACK_CAMERA
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val handler = Handler(Looper.getMainLooper())
    private var currentLetter: String? = null
    private var letterStartTime: Long = 0
    private val scanDuration: Long = 2000

    override fun beforeSomething() {
        super.beforeSomething()

        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 400
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 400
        }
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    private fun startCamera() {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireActivity(),
            classifierListener = object :
                ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    activity?.runOnUiThread {
                        showToast(
                            requireActivity(),
                            error
                        )
                    }
                }

                override fun onResults(
                    results: List<ImageClassifierHelper.Classifications>,
                    inferenceTime: Long
                ) {
                    activity?.runOnUiThread {
                        results.let {
                            if (it.isNotEmpty()) {
                                val displayResult = it.first().label

                                // This commented code is for displaying
                                // Every analyzed output model

//                                    it.joinToString("\n") { classification ->
//                                        "${classification.label}: ${
//                                            NumberFormat.getPercentInstance()
//                                                .format(classification.score)
//                                        }"
//                                    }

                                binding.categoryText.text = displayResult
                                binding.scoreText.text =
                                    "$inferenceTime ms"

                                appendCategory(displayResult)
                            } else {
                                resetTimer()
                                binding.categoryText.text = ""
                                binding.scoreText.text = ""
                            }
                        }
                    }
                }
            }
        )

        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider =
                cameraProviderFuture.get()
            val resolutionSelector = ResolutionSelector.Builder()
                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                .build()
            val imageAnalyzer = ImageAnalysis.Builder()
                .setResolutionSelector(resolutionSelector)
                .setTargetRotation(binding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
            imageAnalyzer.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                imageClassifierHelper.classifyImage(image)
            }

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                showToast(
                    requireActivity(),
                    "Failed to bind camera use cases",
                )
                Log.e(TAG, "Binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireActivity()))
    }

    override fun doSomething() {
        super.doSomething()

        initListener()
    }

    private fun initListener() {
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


        binding.btnSave.setOnClickListener {
            val appended = binding.edResults.text
            if (appended.isNotEmpty()) {
                val extras = FragmentNavigatorExtras(
                    binding.contentLayoutText to "content_layout_shared",
                    binding.btnSave to "btn_save",
                )
                val direction =
                    AnalyzeCNNFragmentDirections.actionAnalyzeCNNFragmentToOutputFragment(
                        output = appended.toString()
                    )
                findNavController().navigate(direction, extras)
            } else {
                showToast(requireActivity(), "Result's Empty!")
            }
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

    companion object {
        private const val TAG = "CameraActivity"
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}