package com.signify.app.presentation.fragment.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.model.auth.LoginRequest
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.databinding.FragmentLoginBinding
import com.signify.app.utils.showToast
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    private val viewModel: LoginViewModel by inject()

    private fun syncBarColor() {
        activity?.window?.statusBarColor = Color.WHITE
        activity?.window?.navigationBarColor =
            context?.getColor(R.color.blackOne)!!
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = true
    }

    override fun onResume() {
        super.onResume()
        syncBarColor()
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

        syncBarColor()
        initListener()
        initAnimation()
        initObserver()
    }

    private fun initObserver() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            when (it) {
                is ApiStatus.Loading -> {
                    binding.loadingView.root.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    showToast(
                        requireActivity(),
                        getString(R.string.greeting_toast, it.data.firstName)
                    )

                    with(binding) {
                        val extras = FragmentNavigatorExtras(
                            contentLayout to "content_layout_shared",
                            circleLeft to "circle_left_shared",
                            circleRight to "circle_right_shared",
                            titleApp to "title_app",
                        )
                        findNavController().navigate(
                            R.id.action_loginFragment_to_homeFragment,
                            null,
                            null,
                            extras
                        )
                    }

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

    private fun initListener() {
        with(binding) {
            edPasswordWrapper.isHintEnabled = false

            btnRegister.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    imageIllustration to "image_illustration_shared",
                    binding.circleLeft to "circle_left_shared",
                    binding.circleRight to "circle_right_shared",
                )
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment,
                    null,
                    null,
                    extras
                )
            }

            btnLogin.setOnClickListener {
                binding.loadingView.root.visibility = View.VISIBLE
                val email = binding.edEmail.text?.trim().toString()
                val password = binding.edPassword.text?.trim().toString()

                val request = LoginRequest(
                    email,
                    password,
                )

                if (email.isEmpty() || password.isEmpty()) {
                    showToast(
                        requireActivity(),
                        getString(R.string.email_or_password_empty)
                    )
                    binding.loadingView.root.visibility = View.GONE
                } else {
                    viewModel.login(request)
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