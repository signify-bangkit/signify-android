package com.signify.app.presentation

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.signify.app.base.BaseActivity
import com.signify.app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun assignBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initSomething() {
        super.initSomething()

        installSplashScreen()
    }
}