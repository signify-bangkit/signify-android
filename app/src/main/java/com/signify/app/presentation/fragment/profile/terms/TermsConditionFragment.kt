package com.signify.app.presentation.fragment.profile.terms

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.ViewGroup
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentTermsConditionBinding

class TermsConditionFragment : BaseFragment<FragmentTermsConditionBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentTermsConditionBinding {
        return FragmentTermsConditionBinding.inflate(inflater, container, false)
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