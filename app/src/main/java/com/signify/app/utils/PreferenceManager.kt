package com.signify.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.signify.app.data.auth.LoginResponse

class PreferenceManager(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    private val editor = prefs.edit()

    fun setStringPreference(prefKey: String, value: String) {
        editor.putString(prefKey, value)
        editor.apply()
    }

    fun setLoginPref(user: LoginResponse) {
        user.let {
            setStringPreference(TOKEN_KEY, it.token)
        }
    }

    fun clearAllPreferences() {
        editor.remove(TOKEN_KEY)
        editor.remove(NAME_KEY)
        editor.apply()
    }

    val getToken = prefs.getString(TOKEN_KEY, "") ?: ""
    val name = prefs.getString(NAME_KEY, "") ?: ""

    companion object {
        const val PREFS_NAME: String = "auth_pref"
        const val TOKEN_KEY: String = "auth_token"
        const val NAME_KEY: String = "auth_name"
    }

}
