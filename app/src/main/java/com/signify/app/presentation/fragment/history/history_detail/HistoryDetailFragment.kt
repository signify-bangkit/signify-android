package com.signify.app.presentation.fragment.history.history_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
    }
}