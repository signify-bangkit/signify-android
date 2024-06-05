package com.signify.app.presentation.fragment.analyze.analyze

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentAnalyzeBinding

class AnalyzeFragment : BaseFragment<FragmentAnalyzeBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAnalyzeBinding {
        return FragmentAnalyzeBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()
        with(binding) {
            btnNavigateOne.setOnClickListener {
                findNavController().navigate(R.id.action_analyzeFragment_to_outputFragment)
            }
        }
    }
}