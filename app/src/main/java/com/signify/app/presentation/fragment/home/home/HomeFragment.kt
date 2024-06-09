package com.signify.app.presentation.fragment.home.home

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun beforeSomething() {
        super.beforeSomething()
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 500
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 500
        }
    }

    override fun doSomething() {
        super.doSomething()

        with(binding) {
            btnNavigateOne.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_articleDetailFragment) }
            btnNavigateTwo.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_analyzeFragment) }
            btnNavigateThree.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_historyFragment) }
        }
    }
}