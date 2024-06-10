package com.signify.app.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.signify.app.data.model.News
import com.signify.app.databinding.ListArticleBinding
import com.signify.app.presentation.fragment.home.home.HomeFragmentDirections

class NewsAdapter(
    private val context: Context,
    private val newsList: List<News>
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val binding = ListArticleBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class NewsViewHolder(private val binding: ListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: News) {
            binding.tvTitleNews.text = newsItem.title
            binding.tvDescriptionNews.text = newsItem.description
            binding.tvAuthorNews.text = newsItem.author
            binding.tvDateNews.text = newsItem.date

            binding.imagePhoto.load(newsItem.imageUrl) {
                transformations(RoundedCornersTransformation(25f))
            }

            binding.imagePhoto.transitionName = "image_shared"

            // Set click listener
            binding.root.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(
                        newsItem.imageUrl
                    )
                it.findNavController().navigate(action)
            }
        }
    }
}
