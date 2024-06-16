package com.signify.app.presentation.fragment.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.auth.RegisterRequest
import com.signify.app.data.basemodel.ApiStatus
import com.signify.app.databinding.FragmentRegisterBinding
import com.signify.app.utils.showToast
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    private val viewModel: RegisterViewModel by inject()

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
        initObserver()
    }

    private fun initListener() {
        with(binding) {
            edPasswordWrapper.isHintEnabled = false

            // Register
            btnSignup.setOnClickListener {
                binding.loadingView.root.visibility = View.VISIBLE

                val name = binding.edName.text.toString()
                val nameLast = binding.edNameLast.text.toString()
                val email = binding.edEmail.text?.trim().toString()
                val password = binding.edPassword.text?.trim().toString()

                val request = RegisterRequest(
                    name,
                    nameLast,
                    email,
                    password,
                )

                if (email.isEmpty() || password.isEmpty()) {
                    showToast(requireActivity(),
                        getString(R.string.email_or_password_empty))
                    binding.loadingView.root.visibility = View.GONE
                } else {
                    viewModel.register(request)
                }
            }
        }
    }

    private fun initObserver() {
        viewModel.registerResult.observe(this) {
            when (it) {
                is ApiStatus.Loading -> {
                    binding.loadingView.root.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    showToast(requireActivity(), it.data.msg)
                    val direction =
                        RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                    findNavController().navigate(direction)
                }

                is ApiStatus.Error -> {
                    showToast(requireActivity(), it.errorMessage)
                    binding.loadingView.root.visibility = View.GONE
                }

                else -> {
                    binding.loadingView.root.visibility = View.GONE
                }
            }
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
        val labelNameLast =
            ObjectAnimator.ofFloat(binding.labelNameLast, View.ALPHA, 1f)
                .setDuration(50)
        val inputNameLast =
            ObjectAnimator.ofFloat(binding.edNameLast, View.ALPHA, 1f)
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
                labelNameLast,
                inputNameLast,
                labelEmail,
                inputEmail,
                labelPassword,
                inputPassword,
                signUpButton,
            )
        }.start()
    }
}