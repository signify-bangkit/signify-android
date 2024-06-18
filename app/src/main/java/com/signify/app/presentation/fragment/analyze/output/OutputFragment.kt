package com.signify.app.presentation.fragment.analyze.output

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.model.history.HistoryRequest
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.databinding.FragmentOutputBinding
import com.signify.app.utils.showToast
import org.koin.android.ext.android.inject

class OutputFragment : BaseFragment<FragmentOutputBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentOutputBinding {
        return FragmentOutputBinding.inflate(inflater, container, false)
    }

    private val viewModel: OutputViewModel by inject()

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
        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        initListener()
        initObserver()
    }

    private fun initListener() {
        val args = OutputFragmentArgs.fromBundle(arguments as Bundle)
        binding.edResults.append(args.output)

        binding.btnSave.setOnClickListener {
            binding.loadingView.root.visibility = View.VISIBLE
            val request = HistoryRequest(binding.edResults.text.toString())
            viewModel.uploadText(request)
        }
    }

    private fun initObserver() {
        viewModel.uploadResult.observe(this) {
            when (it) {
                is ApiStatus.Loading -> {
                    binding.loadingView.root.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    showToast(requireActivity(), "Saved!")

                    with(binding) {
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
}