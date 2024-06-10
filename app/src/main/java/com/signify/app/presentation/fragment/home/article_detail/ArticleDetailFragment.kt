package com.signify.app.presentation.fragment.home.article_detail

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import coil.transform.RoundedCornersTransformation
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : BaseFragment<FragmentArticleDetailBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentArticleDetailBinding {
        return FragmentArticleDetailBinding.inflate(inflater, container, false)
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
        val args = ArticleDetailFragmentArgs.fromBundle(arguments as Bundle)
        binding.imagePhoto.load(args.imageUrl) {
            transformations(RoundedCornersTransformation(25f))
        }

        initListener()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

}