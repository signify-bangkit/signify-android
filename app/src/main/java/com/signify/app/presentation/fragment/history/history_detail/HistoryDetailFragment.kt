package com.signify.app.presentation.fragment.history.history_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentHistoryDetailBinding

class HistoryDetailFragment : BaseFragment<FragmentHistoryDetailBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHistoryDetailBinding {
        return FragmentHistoryDetailBinding.inflate(inflater, container, false)
    }

    override fun doSomething() {
        super.doSomething()

        initListener()
    }

    private fun initListener() {
        val args = HistoryDetailFragmentArgs.fromBundle(arguments as Bundle)

        with(binding) {
            authorText.text = resources.getString(R.string.history_author_date, args.date)
            translatedText.text = args.translatedText

            btnBack.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }
}