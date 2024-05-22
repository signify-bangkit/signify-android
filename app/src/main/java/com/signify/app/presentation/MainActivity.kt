package com.signify.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.signify.app.R
import com.signify.app.base.BaseActivity
import com.signify.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun assignBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}