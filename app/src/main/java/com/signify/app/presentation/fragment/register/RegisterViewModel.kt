package com.signify.app.presentation.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.repository.AuthRepository
import com.signify.app.data.model.auth.RegisterRequest
import com.signify.app.data.model.base.ApiResponse
import com.signify.app.data.model.base.ApiStatus
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiStatus<ApiResponse>>()
    val registerResult: LiveData<ApiStatus<ApiResponse>> = _registerResult

    fun register(user: RegisterRequest) {
        viewModelScope.launch {
            repository.register(user).collect {
                _registerResult.value = it
            }
        }
    }
}
