package com.signify.app.presentation.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.mediapipe.tasks.components.containers.Category
import com.signify.app.databinding.FragmentRecognizerResultBinding
import java.util.Locale
import kotlin.math.min

class GestureRecognizerAdapter :
    RecyclerView.Adapter<GestureRecognizerAdapter.ViewHolder>() {

    companion object {
        private const val NO_VALUE = "--"
    }

    private var adapterCategories: MutableList<Category?> = mutableListOf()
    private var adapterSize: Int = 0
    private var recognizedText: String = ""

    private val handler = Handler(Looper.getMainLooper())
    private var currentLetter: String? = null
    private var letterStartTime: Long = 0
    private val scanDuration: Long = 1500

    @SuppressLint("NotifyDataSetChanged")
    fun updateResults(categories: List<Category>?) {
        adapterCategories = MutableList(adapterSize) { null }
        if (categories != null) {
            val sortedCategories = categories.sortedByDescending { it.score() }
            val min = min(sortedCategories.size, adapterCategories.size)
            for (i in 0 until min) {
                adapterCategories[i] = sortedCategories[i]
            }
            adapterCategories.sortedBy { it?.index() }
            notifyDataSetChanged()
        }
    }

    fun updateAdapterSize(size: Int) {
        adapterSize = size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = FragmentRecognizerResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapterCategories[position]?.let { category ->
            holder.bind(category.categoryName(), category.score())
            startLetterRecognition(category.categoryName())
        }
    }

    override fun getItemCount(): Int = adapterCategories.size

    private fun startLetterRecognition(letter: String?) {
        if (letter == null || !letter.matches(Regex("[a-zA-Z0-9]"))) {
            resetTimer()
            return
        }

        if (letter == currentLetter) {
            if (System.currentTimeMillis() - letterStartTime >= scanDuration) {
                addLetterToTextLabel(letter)
                resetTimer()
            }
        } else {
            resetTimer()
            currentLetter = letter
            letterStartTime = System.currentTimeMillis()
            handler.postDelayed({
                if (currentLetter == letter) {
                    addLetterToTextLabel(letter)
                    resetTimer()
                }
            }, scanDuration)
        }
    }

    private fun resetTimer() {
        currentLetter = null
        letterStartTime = 0
        handler.removeCallbacksAndMessages(null)
    }

    private fun addLetterToTextLabel(letter: String) {
        recognizedText += letter
        // Use post to ensure it is called after the current layout pass
        handler.post {
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(private val binding: FragmentRecognizerResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(label: String?, score: Float?) {
            with(binding) {
                tvLabel.text = label ?: NO_VALUE
                tvScore.text = if (score != null) String.format(
                    Locale.US,
                    "%.2f",
                    score
                ) else NO_VALUE

                textLabel.text = recognizedText
            }
        }
    }
}