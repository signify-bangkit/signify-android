package com.signify.app.presentation.fragment.analyze.analyze

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.signify.app.R
import com.signify.app.databinding.ActivityAnalyzeBinding
import com.signify.app.presentation.fragment.analyze.AnalyzeViewModel

class AnalyzeActivity : AppCompatActivity() {
    private lateinit var activityAnalyzeBinding: ActivityAnalyzeBinding
    private val viewModel: AnalyzeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAnalyzeBinding = ActivityAnalyzeBinding.inflate(layoutInflater)
        setContentView(activityAnalyzeBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityAnalyzeBinding.navigation.setupWithNavController(navController)
        activityAnalyzeBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection
        }
//
//
//        findViewById<Button>(R.id.btnDeleteWord).setOnClickListener(
//            deletebuttonClick()
//        )
//
//        findViewById<Button>(R.id.addSpaceButton).setOnClickListener(
//            addSpacebuttonClick()
//        )
    }

    //    private fun deletebuttonClick(): View.OnClickListener {
//        return View.OnClickListener {
//            Log.d("MainActivity", "Delete button clicked")
//            val textLabel = findViewById<TextView>(R.id.textLabel)
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
//    private fun addSpacebuttonClick(): View.OnClickListener {
//        return View.OnClickListener {
//            Log.d("MainActivity", "Add space button clicked")
//            val textLabel = findViewById<TextView>(R.id.textLabel)
//            textLabel.text = textLabel.text.toString() + " "
//        }
//    }
}