package com.signify.app.presentation.fragment.profile

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
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
    }

    private fun initListener() {
        with(binding) {
            btnAnalyze.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(R.id.action_global_analyzeFragment, null,null, extras)
            }
            btnHome.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(R.id.action_global_homeFragment, null, null, extras)
            }
            btnHistory.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_historyFragment,
                    null,
                    null,
                    extras
                )
            }
        }
    }
}