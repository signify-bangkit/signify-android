package com.signify.app.presentation.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
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

    override fun doSomething() {
        super.doSomething()

        initListener()
    }

    private fun initListener() {
    }
}