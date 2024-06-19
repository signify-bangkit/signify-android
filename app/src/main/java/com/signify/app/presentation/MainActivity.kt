package com.signify.app.presentation

import android.content.pm.ActivityInfo
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

    override fun doSomething() {
        super.doSomething()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
    }
}