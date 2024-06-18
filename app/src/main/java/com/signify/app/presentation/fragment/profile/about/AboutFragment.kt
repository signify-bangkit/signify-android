package com.signify.app.presentation.fragment.profile.about

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment<FragmentAboutBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(inflater, container, false)
    }

    private fun syncBarColor(out: Boolean) {

        activity?.window?.navigationBarColor =
            context?.getColor(if (out) R.color.white else R.color.blueOne)!!
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncBarColor(false)
    }

    override fun onStop() {
        super.onStop()
        syncBarColor(true)
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

}