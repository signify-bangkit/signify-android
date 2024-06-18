package com.signify.app.presentation.fragment.convert

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentConvertBinding
import com.signify.app.utils.showToast

class ConvertFragment : BaseFragment<FragmentConvertBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentConvertBinding {
        return FragmentConvertBinding.inflate(inflater, container, false)
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
            btnConvert.setOnClickListener {
                contentLayout.visibility = View.VISIBLE
                loadDrawable(edResults.text.toString().trim(), convertedIv)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun loadDrawable(text: String, imageView: ImageView) {
        val drawableResId = resources.getIdentifier(
            text.lowercase(),
            "drawable",
            requireActivity().packageName
        )
        if (drawableResId != 0) {
            imageView.setImageResource(drawableResId)
        } else {
            binding.contentLayout.visibility = View.GONE
            showToast(requireActivity(), "Not Supported")
        }
    }
}