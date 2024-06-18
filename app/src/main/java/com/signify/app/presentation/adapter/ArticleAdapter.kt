package com.signify.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.signify.app.data.model.article.Article
import com.signify.app.data.model.article.ArticleResponseItem
import com.signify.app.databinding.ListArticleBinding
import com.signify.app.presentation.fragment.home.home.HomeFragmentDirections
import com.signify.app.utils.toFormattedDate

class ArticleAdapter :
    ListAdapter<ArticleResponseItem, ArticleAdapter.ArticleViewHolder>(
        DIFF_CALLBACK
    ) {

    class ArticleViewHolder(val binding: ListArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticleResponseItem) {
            with(binding) {
                val seconds = item.createdAt.seconds.toLong()

                imageItemLabel.text = item.title.first().uppercase()
                tvTitleNews.text = item.title
                tvDescriptionNews.text = item.content
                tvDateNews.text = seconds.toFormattedDate()

                val item = Article(
                    title = item.title,
                    content = item.content,
                    date = item.createdAt.seconds.toLong().toFormattedDate(),
                    imageUrl = item.imageUrl ?: "",
                )

                // Set click listener
                binding.root.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToArticleDetailFragment(
                            item.imageUrl!!,
                            item.title,
                            item.content,
                            item.date!!,
                        )
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<ArticleResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: ArticleResponseItem, newItem: ArticleResponseItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ArticleResponseItem, newItem: ArticleResponseItem
                ): Boolean {
                    return oldItem == newItem
                }
            }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ArticleViewHolder {
        val itemArticle = ListArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticleViewHolder(itemArticle)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }
}
