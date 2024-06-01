package com.signify.app.presentation.fragment.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()

        initListener()
        initAnimation()
    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener {
            val direction =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(direction)
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
            ObjectAnimator.ofFloat(binding.edPassword, View.ALPHA, 1f)
                .setDuration(50)
        val signInButton =
            ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f)
                .setDuration(50)
        val signUpButton =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f)
                .setDuration(50)

        AnimatorSet().apply {
            playSequentially(
                contentOne,
                contentTwo,
                labelEmail,
                inputEmail,
                labelPassword,
                inputPassword,
                signInButton,
                signUpButton
            )
        }.start()
    }
}