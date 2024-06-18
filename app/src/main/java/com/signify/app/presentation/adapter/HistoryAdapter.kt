package com.signify.app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.signify.app.R
import com.signify.app.data.model.history.History
import com.signify.app.databinding.ListHistoryBinding
import com.signify.app.presentation.fragment.history.history.HistoryFragmentDirections
import com.signify.app.utils.toFormattedDate

class HistoryAdapter :
    ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    class HistoryViewHolder(val binding: ListHistoryBinding) :
        ViewHolder(binding.root) {
        fun bind(item: History) {
            with(binding) {
                val seconds = item.createdAt.seconds.toLong()

                authorText.text = binding.root.context.getString(
                    R.string.history_author_date, seconds.toFormattedDate()
                )
                descriptionText.text = item.translatedText

                // Set click listener
                binding.root.setOnClickListener {
                    val action =
                        HistoryFragmentDirections.actionHistoryFragmentToHistoryDetailFragment(
                            seconds.toFormattedDate(),
                            item.translatedText,
                            item.id
                        )
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(
                oldItem: History, newItem: History
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: History, newItem: History
            ): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): HistoryViewHolder {
        val itemHistory = ListHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(itemHistory)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item)
    }
}