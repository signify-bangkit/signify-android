package com.signify.app.presentation.fragment.analyze.analyze

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.signify.app.R
import com.signify.app.databinding.FragmentRecognizerResultBinding

class RecognizerResultFragment : Fragment() {

    private lateinit var binding: FragmentRecognizerResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecognizerResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDeleteWord.setOnClickListener(
            deleteButtonClick()
        )
        binding.addSpaceButton.setOnClickListener(
            addSpaceButtonClick()
        )
    }

    private fun deleteButtonClick(): View.OnClickListener {
        return View.OnClickListener {
            Log.d("YourFragment", "Delete button clicked")
            val textLabel = binding.textLabel
            val currentText = textLabel.text.toString()

            if (currentText.isNotEmpty()) {
                val updatedText = currentText.substring(0, currentText.length - 1)
                textLabel.text = updatedText
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addSpaceButtonClick(): View.OnClickListener {
        return View.OnClickListener {
            Log.d("YourFragment", "Add space button clicked")
            val textLabel = binding.textLabel
            textLabel.text = textLabel.text.toString() + " "
        }
    }
}
