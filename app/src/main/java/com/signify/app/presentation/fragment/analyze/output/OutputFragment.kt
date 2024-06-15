package com.signify.app.presentation.fragment.analyze.output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentOutputBinding

class OutputFragment : BaseFragment<FragmentOutputBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOutputBinding {
        return FragmentOutputBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()
        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }
}