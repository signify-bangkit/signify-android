package com.signify.app.presentation.fragment.auth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
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
        activity?.window?.statusBarColor = Color.GRAY
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    }

    override fun doSomething() {
        binding.btnLogin.setOnClickListener {
            val direction =
                AuthFragmentDirections.actionAuthFragmentToLoginFragment()
            findNavController().navigate(direction)
        }
    }
}