package com.signify.app.presentation.fragment.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun beforeSomething() {
        super.beforeSomething()
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 400
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 400
        }
    }

    override fun doSomething() {
        super.doSomething()

        initListener()
        initAnimation()
    }

    private fun initListener() {
        with(binding) {
            edPasswordWrapper.isHintEnabled = false
            edConfirmPasswordWrapper.isHintEnabled = false
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
                .setDuration(50)
        val contentTwo =
            ObjectAnimator.ofFloat(binding.contentTwo, View.ALPHA, 1f)
                .setDuration(50)
        val labelName =
            ObjectAnimator.ofFloat(binding.labelName, View.ALPHA, 1f)
                .setDuration(50)
        val inputName =
            ObjectAnimator.ofFloat(binding.edName, View.ALPHA, 1f)
                .setDuration(50)
        val labelEmail =
            ObjectAnimator.ofFloat(binding.labelEmail, View.ALPHA, 1f)
                .setDuration(50)
        val inputEmail =
            ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f)
                .setDuration(50)
        val labelPassword =
            ObjectAnimator.ofFloat(binding.labelPassword, View.ALPHA, 1f)
                .setDuration(50)
        val inputPassword =
            ObjectAnimator.ofFloat(binding.edPasswordWrapper, View.ALPHA, 1f)
                .setDuration(50)
        val labelConfirmPassword =
            ObjectAnimator.ofFloat(binding.labelConfirmPassword, View.ALPHA, 1f)
                .setDuration(50)
        val inputConfirmPassword =
            ObjectAnimator.ofFloat(
                binding.edConfirmPasswordWrapper,
                View.ALPHA,
                1f
            )
                .setDuration(50)
        val signUpButton =
            ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f)
                .setDuration(50)

        AnimatorSet().apply {
            playSequentially(
                contentOne,
                contentTwo,
                labelName,
                inputName,
                labelEmail,
                inputEmail,
                labelPassword,
                inputPassword,
                labelConfirmPassword,
                inputConfirmPassword,
                signUpButton,
            )
        }.start()
    }
}