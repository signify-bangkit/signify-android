package com.signify.app.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.signify.app.data.model.article.ArticleResponse
import com.signify.app.databinding.ListArticleBinding
import com.signify.app.presentation.fragment.home.home.HomeFragmentDirections

class NewsAdapter(
    private val context: Context,
    private val articleResponseList: List<ArticleResponse>
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
        val newsItem = articleResponseList[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return articleResponseList.size
    }

    inner class NewsViewHolder(private val binding: ListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articleResponseItem: ArticleResponse) {
            binding.tvTitleNews.text = articleResponseItem.title
            binding.tvDescriptionNews.text = articleResponseItem.description
            binding.tvDateNews.text = articleResponseItem.date

//            binding.imagePhoto.load(articleResponseItem.imageUrl) {
//                transformations(RoundedCornersTransformation(25f))
//            }
            binding.imageItemLabel.text = articleResponseItem.title.first().toString().uppercase()

            // Set click listener
            binding.root.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(
                        articleResponseItem.imageUrl
                    )
                it.findNavController().navigate(action)
            }
        }
    }
}
