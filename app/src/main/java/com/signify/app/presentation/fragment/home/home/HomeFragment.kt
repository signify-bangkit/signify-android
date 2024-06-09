package com.signify.app.presentation.fragment.home.home

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

    private fun syncBarColor() {
        activity?.window?.statusBarColor = Color.TRANSPARENT
        activity?.window?.navigationBarColor =
            context?.getColor(R.color.white)!!
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncBarColor()

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

        with(binding) {
            btnAnalyze.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_analyzeFragment) }
            btnHistory.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_historyFragment) }
//            btnPerson.setOnClickListener { findNavController().navigate(R.id.action) }
        }

        initAnimation()
    }


    private fun initAnimation() {
        val circleOne =
            ObjectAnimator.ofFloat(binding.circleLeft, View.ALPHA, 0f)
                .setDuration(550)
        val circleTwo =
            ObjectAnimator.ofFloat(binding.circleRight, View.ALPHA, 0f)
                .setDuration(550)

        AnimatorSet().apply {
            playTogether(
                circleOne,
                circleTwo,
            )
        }.start()
    }
}