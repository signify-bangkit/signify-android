package com.signify.app.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.signify.app.data.article.Article
import com.signify.app.databinding.ListArticleBinding
import com.signify.app.presentation.fragment.home.home.HomeFragmentDirections

class NewsAdapter(
    private val context: Context,
    private val articleList: List<Article>
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
        val newsItem = articleList[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class NewsViewHolder(private val binding: ListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleItem: Article) {
            binding.tvTitleNews.text = articleItem.title
            binding.tvDescriptionNews.text = articleItem.description
            binding.tvDateNews.text = articleItem.date

            binding.imagePhoto.load(articleItem.imageUrl) {
                transformations(RoundedCornersTransformation(25f))
            }

            binding.imagePhoto.transitionName = "image_shared"

            // Set click listener
            binding.root.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(
                        articleItem.imageUrl
                    )
                it.findNavController().navigate(action)
            }
        }
    }
}
