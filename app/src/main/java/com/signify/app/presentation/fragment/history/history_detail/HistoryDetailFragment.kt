package com.signify.app.presentation.fragment.history.history_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.databinding.FragmentHistoryDetailBinding
import com.signify.app.utils.showToast
import org.koin.android.ext.android.inject

class HistoryDetailFragment : BaseFragment<FragmentHistoryDetailBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHistoryDetailBinding {
        return FragmentHistoryDetailBinding.inflate(inflater, container, false)
    }

    private val viewModel: HistoryDetailViewModel by inject()

    override fun doSomething() {
        super.doSomething()

        initListener()
        initObserver()
    }

    private fun initListener() {
        val args = HistoryDetailFragmentArgs.fromBundle(arguments as Bundle)

        with(binding) {
            authorText.text =
                resources.getString(R.string.history_author_date, args.date)
            translatedText.text = args.translatedText

            btnBack.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }

            btnDelete.setOnClickListener {
                binding.loadingView.visibility = View.VISIBLE
                viewModel.deleteHistory(args.id)
            }
        }
    }

    private fun initObserver() {
        viewModel.deleteResult.observe(this) {
            when (it) {
                is ApiStatus.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    showToast(requireActivity(), it.data.msg)

                    with(binding) {
                        val extras = FragmentNavigatorExtras(
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
                    binding.loadingView.visibility = View.GONE
                }

                else -> {
                    binding.loadingView.visibility = View.GONE
                }
            }
        }
    }
}