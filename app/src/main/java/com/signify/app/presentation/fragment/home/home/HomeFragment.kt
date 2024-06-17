package com.signify.app.presentation.fragment.home.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.model.article.ArticleResponse
import com.signify.app.databinding.FragmentHomeBinding
import com.signify.app.presentation.adapter.CarouselAdapter
import com.signify.app.presentation.adapter.NewsAdapter
import com.signify.app.utils.PreferenceManager
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private fun syncBarColor() {
        activity?.window?.statusBarColor =
            context?.getColor(R.color.blueOne)!!
        activity?.window?.navigationBarColor =
            context?.getColor(R.color.white)!!
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    private lateinit var carouselRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val pref: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        syncBarColor()

        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        ).isAppearanceLightStatusBars = false
    }

    override fun onResume() {
        super.onResume()
        syncBarColor()
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

        newsAdapter = NewsAdapter(requireContext(), getDummyNews())

        initAnimation()
        initListener()
        setupCarouselRecyclerView()
        setupNewsRecyclerView()
    }

    private fun initListener() {
        with(binding) {
            toolbarTitle.text = getString(R.string.greeting_with_name, pref.getName)
            btnAnalyze.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
                    toolbarTitle to "title_app",
                )
                findNavController().navigate(R.id.action_global_analyzeFragment, null,null, extras)
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
            btnPerson.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    contentLayout to "content_layout_shared",
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


    private fun initAnimation() {
        val circleOne =
            ObjectAnimator.ofFloat(binding.circleLeft, View.ALPHA, 0f)
                .setDuration(550)
        val circleTwo =
            ObjectAnimator.ofFloat(binding.circleRight, View.ALPHA, 0f)
                .setDuration(550)

        AnimatorSet().apply {
            playTogether(
                circleOne,
                circleTwo,
            )
        }.start()
    }

    private fun setupCarouselRecyclerView() {
        carouselRecyclerView = binding.carouselRv
        val carouselLayoutManager =
            CarouselLayoutManager(HeroCarouselStrategy())
        carouselRecyclerView.layoutManager = carouselLayoutManager
        CarouselSnapHelper().attachToRecyclerView(carouselRecyclerView)
        carouselRecyclerView.adapter =
            CarouselAdapter(images = getImages()) { imageUrl ->
                val action =
                    HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(
                        imageUrl
                    )
                findNavController().navigate(action)
            }
    }

    private fun getImages(): List<String> {
        return listOf(
            "https://www.rickhansen.com/sites/default/files/2021-09/happy-fall-asl.png",
            "https://lh3.googleusercontent.com/proxy/v7HoItKe74J2_ADoPzYhn0Ax5VBqRgixwpBgSuqSPbQS5cq8-r7sEVakWmsKCt5B8X2lmIJvoQJS7mWwDZimh9hVIQHDhsUVClGC"
        )
    }

    private fun setupNewsRecyclerView() {
        binding.recyclerNews.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.recyclerNews.adapter = newsAdapter
    }

    private fun getDummyNews(): MutableList<ArticleResponse> {
        return mutableListOf(
            ArticleResponse(
                title = "Not",
                description = "Description 1",
                date = "Date 1",
                imageUrl = "https://i.pinimg.com/564x/67/9c/dc/679cdc274ea67a113a9cd98ef61ec894.jpg"
            ),
            ArticleResponse(
                title = "Implemented",
                description = "Description 2",
                date = "Date 2",
                imageUrl = "https://i.pinimg.com/564x/06/6f/b2/066fb2bd6a2f623a340239ed25db389d.jpg"
            ),
            ArticleResponse(
                title = "Yet",
                description = "Description 3",
                date = "Date 3",
                imageUrl = "https://i.pinimg.com/564x/aa/48/fe/aa48fe1ed1162094d329a1cbe720bf8c.jpg"
            ),
        )
    }
}