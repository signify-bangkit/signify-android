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
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.databinding.FragmentHomeBinding
import com.signify.app.presentation.adapter.ArticleAdapter
import com.signify.app.presentation.adapter.CarouselAdapter
import com.signify.app.utils.BottomMarginDecoration
import com.signify.app.utils.PreferenceManager
import com.signify.app.utils.dummyData
import com.signify.app.utils.showToast
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
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var carouselAdapter: CarouselAdapter
    private val pref: PreferenceManager by inject()
    private val viewModel: HomeViewModel by inject()

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

        articleAdapter = ArticleAdapter()
        carouselAdapter = CarouselAdapter()

        initAnimation()
        initListener()

        setupCarouselRecyclerView()
        initObserver()
    }

    private fun initObserver() {
        setupArticleRecyclerView()

        viewModel.results.observe(viewLifecycleOwner) {

            when (it) {
                is ApiStatus.Loading -> {
                    binding.loadingView.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    binding.loadingView.visibility = View.GONE
                    articleAdapter.submitList(it.data.results)
                    binding.recyclerNews.addItemDecoration(
                        BottomMarginDecoration(150)
                    )
                }

                is ApiStatus.Error -> {
                    showToast(requireActivity(), it.errorMessage)
                    binding.loadingView.visibility = View.GONE
                    binding.emptyText.visibility = View.VISIBLE
                    binding.emptyTvLabel.text =
                        getString(R.string.server_side, it.errorMessage)
                }

                else -> {
                    binding.loadingView.visibility = View.GONE
                }
            }
        }
    }

    private fun initListener() {
        with(binding) {
            toolbarTitle.text =
                getString(R.string.greeting_with_name, pref.getFirstName)

            val extras = FragmentNavigatorExtras(
                contentLayout to "content_layout_shared",
                toolbarTitle to "title_app",
            )

            btnAnalyze.setOnClickListener {
                findNavController().navigate(
                    R.id.action_global_analyzeFragment,
                    null,
                    null,
                    extras
                )
            }
            btnHistory.setOnClickListener {
                findNavController().navigate(
                    R.id.action_global_historyFragment,
                    null,
                    null,
                    extras
                )
            }
            btnPerson.setOnClickListener {
                findNavController().navigate(
                    R.id.action_global_profileFragment,
                    null,
                    null,
                    extras
                )
            }
            btnConvert.setOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToConvertFragment(),
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
        carouselRecyclerView.adapter = carouselAdapter
        carouselAdapter.submitList(dummyData)
    }

    private fun setupArticleRecyclerView() {
        binding.recyclerNews.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.recyclerNews.adapter = articleAdapter
    }

}