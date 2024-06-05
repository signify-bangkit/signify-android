package com.signify.app.presentation.fragment.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.R
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

            btnRegister.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    imageIllustration to "image_illustration_shared"
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment,
                    null,
                    null,
                    extras
                )
            }

            btnLogin.setOnClickListener {

                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                )
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment, null, null, extras)
            }

            // focus listener, weird this bug happened sometimes
            //edPassword.apply {
            //    setOnFocusChangeListener { _, hasFocus ->
            //        edPasswordWrapper.isHintEnabled =
            //            !(hasFocus || edPassword.text!!.isNotEmpty())
            //    }
            //}
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
            ObjectAnimator.ofFloat(binding.edPasswordWrapper, View.ALPHA, 1f)
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