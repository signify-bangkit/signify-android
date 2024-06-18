package com.signify.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.signify.app.data.model.auth.LoginResponse
import com.signify.app.data.model.auth.UpdateProfileRequest

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
            setStringPreference(FIRST_NAME_KEY, it.firstName)
            setStringPreference(LAST_NAME_KEY, it.lastName)
            setStringPreference(EMAIL_KEY, it.email)
        }
    }

    fun updateNamePref(profile: UpdateProfileRequest) {
        profile.let {
            setStringPreference(FIRST_NAME_KEY, it.firstName)
            setStringPreference(LAST_NAME_KEY, it.lastName)
        }
    }

    fun clearAllPreferences() {
        editor.remove(TOKEN_KEY)
        editor.remove(FIRST_NAME_KEY)
        editor.remove(LAST_NAME_KEY)
        editor.remove(EMAIL_KEY)
        editor.apply()
    }

    val getToken = prefs.getString(TOKEN_KEY, "") ?: ""
    val getFirstName = prefs.getString(FIRST_NAME_KEY, "") ?: ""
    val getLastName = prefs.getString(LAST_NAME_KEY, "") ?: ""
    val getEmail = prefs.getString(EMAIL_KEY, "") ?: ""

    companion object {
        const val PREFS_NAME: String = "auth_pref"
        const val TOKEN_KEY: String = "auth_token"
        const val FIRST_NAME_KEY: String = "auth_first_name"
        const val LAST_NAME_KEY: String = "auth_last_name"
        const val EMAIL_KEY: String = "auth_email"
    }

}
