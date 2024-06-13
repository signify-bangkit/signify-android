package com.signify.app.presentation.fragment.home.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.data.model.News
import com.signify.app.databinding.FragmentHomeBinding
import com.signify.app.presentation.adapter.CarouselAdapter
import com.signify.app.presentation.adapter.NewsAdapter
import com.signify.app.presentation.fragment.analyze.analyze.AnalyzeActivity

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
            context?.getColor(R.color.blueOneBar)!!
        activity?.window?.navigationBarColor =
            context?.getColor(R.color.white)!!
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    private lateinit var carouselRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

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

        with(binding) {
//            btnAnalyze.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_analyzeFragment) }
            btnAnalyze.setOnClickListener {
                val intent = Intent(requireContext(), AnalyzeActivity::class.java)
                startActivity(intent)
            }
            btnHistory.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_historyFragment) }
//            btnPerson.setOnClickListener { findNavController().navigate(R.id.action) }
        }

        newsAdapter = NewsAdapter(requireContext(), getDummyNews())

        initAnimation()
        setupCarouselRecyclerView()
        setupNewsRecyclerView()
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

    private fun getDummyNews(): MutableList<News> {
        return mutableListOf(
            News(
                title = "Title 1",
                description = "Description 1",
                author = "Author 1",
                date = "Date 1",
                imageUrl = "https://i.pinimg.com/564x/67/9c/dc/679cdc274ea67a113a9cd98ef61ec894.jpg"
            ),
            News(
                title = "Title 2",
                description = "Description 2",
                author = "Author 2",
                date = "Date 2",
                imageUrl = "https://i.pinimg.com/564x/06/6f/b2/066fb2bd6a2f623a340239ed25db389d.jpg"
            ),
            News(
                title = "Title 3",
                description = "Description 3",
                author = "Author 3",
                date = "Date 3",
                imageUrl = "https://i.pinimg.com/564x/aa/48/fe/aa48fe1ed1162094d329a1cbe720bf8c.jpg"
            ),
            News(
                title = "Title 4",
                description = "Description 4",
                author = "Author 4",
                date = "Date 4",
                imageUrl = "https://i.pinimg.com/564x/67/9c/dc/679cdc274ea67a113a9cd98ef61ec894.jpg"
            ),
            News(
                title = "Title 5",
                description = "Description 5",
                author = "Author 5",
                date = "Date 5",
                imageUrl = "https://i.pinimg.com/564x/95/68/6a/95686a79fda78c1d70ca6bbc09587977.jpg"
            ),
            News(
                title = "Title 6",
                description = "Description 5",
                author = "Author 5",
                date = "Date 5",
                imageUrl = "https://i.pinimg.com/564x/67/9c/dc/679cdc274ea67a113a9cd98ef61ec894.jpg"
            ),
        )
    }
}