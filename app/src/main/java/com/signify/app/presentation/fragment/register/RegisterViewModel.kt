package com.signify.app.presentation.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.auth.AuthRepository
import com.signify.app.data.auth.RegisterRequest
import com.signify.app.data.auth.RegisterResponse
import com.signify.app.data.basemodel.ApiStatus
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _registerResult = MutableLiveData<ApiStatus<RegisterResponse>>()
    val registerResult: LiveData<ApiStatus<RegisterResponse>> = _registerResult

    fun register(user: RegisterRequest) {
        viewModelScope.launch {
            repository.register(user).collect {
                _registerResult.value = it
            }
        }
    }
}
