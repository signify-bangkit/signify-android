package com.signify.app.presentation.fragment.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentAuthBinding

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.statusBarColor = Color.TRANSPARENT
        activity?.window?.navigationBarColor =
            context?.getColor(R.color.blackOne)!!
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = true

    }

    override fun doSomething() {
        initListener()
        initAnimation()

        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }

    private fun initListener() {
        binding.btnGetStarted.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                binding.contentLayout to "content_layout_shared",
                binding.imageIllustration to "image_illustration_shared",
                binding.circleLeft to "circle_left_shared",
                binding.circleRight to "circle_right_shared",
                binding.titleApp to "title_app",
            )

            val direction = AuthFragmentDirections.actionAuthFragmentToLoginFragment()

            findNavController().navigate(
                direction,
                extras
            )
        }
    }

    private fun initAnimation() {
        ObjectAnimator.ofFloat(
            binding.imageIllustration, View.TRANSLATION_X, -10f, 10f
        ).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val contentOne =
            ObjectAnimator.ofFloat(binding.contentOne, View.ALPHA, 1f)
                .setDuration(100)
        val contentTwo =
            ObjectAnimator.ofFloat(binding.contentTwo, View.ALPHA, 1f)
                .setDuration(100)
        val getStartedButton =
            ObjectAnimator.ofFloat(binding.btnGetStarted, View.ALPHA, 1f)
                .setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                contentOne, contentTwo, getStartedButton
            )
        }.start()
    }
}