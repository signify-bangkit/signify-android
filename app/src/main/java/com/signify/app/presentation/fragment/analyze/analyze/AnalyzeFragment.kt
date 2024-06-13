package com.signify.app.presentation.fragment.analyze.analyze

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.signify.app.R
import com.signify.app.base.BaseFragment
import com.signify.app.databinding.FragmentAnalyzeBinding
import com.signify.app.presentation.fragment.analyze.AnalyzeViewModel

class AnalyzeFragment : BaseFragment<FragmentAnalyzeBinding>() {

    private val viewModel: AnalyzeViewModel by viewModels()

    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAnalyzeBinding {
        return FragmentAnalyzeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

//        val textLabel = binding.textLabel
//        Log.d("tes", textLabel.toString())

//    private fun deleteButtonClick(): View.OnClickListener {
//        return View.OnClickListener {
//            Log.d("MainFragment", "Delete button clicked")
//            val textLabel = binding.textLabel
//            val currentText = textLabel.text.toString()
//
//            if (currentText.isNotEmpty()) {
//                val updatedText = currentText.substring(0, currentText.length - 1)
//                textLabel.text = updatedText
//            }
//        }
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun addSpaceButtonClick(): View.OnClickListener {
//        return View.OnClickListener {
//            Log.d("MainFragment", "Add space button clicked")
//            val textLabel = binding.textLabel
//            textLabel.text = textLabel.text.toString() + " "
//        }
//    }
}