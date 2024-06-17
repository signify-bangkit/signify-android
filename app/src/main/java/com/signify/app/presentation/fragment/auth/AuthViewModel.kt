package com.signify.app.presentation.fragment.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.signify.app.utils.PreferenceManager
import com.signify.app.utils.reloadKoinModules
import org.koin.core.context.unloadKoinModules

class AuthViewModel(val pref: PreferenceManager) : ViewModel() {

    val _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun getToken() {
        _isLogin.value = pref.getToken.isNotEmpty()
    }

    fun logout(): Boolean {
        return try {
            pref.clearAllPreferences()
            reloadKoinModules()

            getToken()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    init {
        getToken()
    }
}
