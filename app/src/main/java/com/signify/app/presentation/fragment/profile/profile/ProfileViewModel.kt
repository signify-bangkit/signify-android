package com.signify.app.presentation.fragment.profile.profile

import androidx.lifecycle.ViewModel
import com.signify.app.data.repository.AuthRepository

class ProfileViewModel(val repository: AuthRepository ): ViewModel() {
    fun logout(): Boolean {
        return repository.logout()
    }
}