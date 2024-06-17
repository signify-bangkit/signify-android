package com.signify.app.presentation.fragment.history.history

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.transition.doOnEnd
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.databinding.FragmentHistoryBinding
import com.signify.app.presentation.adapter.HistoryAdapter
import com.signify.app.utils.BottomMarginDecoration
import com.signify.app.utils.showToast
import org.koin.android.ext.android.inject

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(inflater, container, false)
    }

    private val viewModel: HistoryViewModel by inject()

    override fun beforeSomething() {
        super.beforeSomething()
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 400
            doOnEnd {
                viewModel.getHistories()
            }
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 400
            doOnEnd {
                viewModel.getHistories()
            }
        }
    }

    override fun doSomething() {
        super.doSomething()
        initListener()
        initObserver()
    }

    private fun initListener() {
        with(binding) {
            btnAnalyze.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_analyzeFragment,
                    null,
                    null,
                    extras
                )
            }
            btnHome.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_homeFragment,
                    null,
                    null,
                    extras
                )
            }
            btnPerson.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(
                    R.id.action_global_profileFragment,
                    null,
                    null,
                    extras
                )
            }
        }
    }

    private fun initObserver() {
        with(binding) {
            val adapter = HistoryAdapter()
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(requireActivity())

            viewModel.results.observe(viewLifecycleOwner) {

                when (it) {
                    is ApiStatus.Loading -> {
                        binding.loadingView.visibility = View.VISIBLE
                    }

                    is ApiStatus.Success -> {
                        binding.loadingView.visibility = View.GONE
                        adapter.submitList(it.data.results)
                        rvHistory.addItemDecoration(BottomMarginDecoration(150))
                    }

                    is ApiStatus.Error -> {
                        showToast(requireActivity(), it.errorMessage)
                        binding.loadingView.visibility = View.GONE
                    }

                    else -> {
                        binding.loadingView.visibility = View.GONE
                    }
                }
            }
        }

    }

}