package com.signify.app.presentation.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.repository.AuthRepository
import com.signify.app.data.model.auth.LoginRequest
import com.signify.app.data.model.auth.LoginResponse
import com.signify.app.data.model.base.ApiStatus
import kotlinx.coroutines.launch

class LoginViewModel(val repository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ApiStatus<LoginResponse>>()
    val loginResult: LiveData<ApiStatus<LoginResponse>> = _loginResult

    fun login(user: LoginRequest) {
        viewModelScope.launch {
            repository.login(user).collect {
                _loginResult.value = it
            }
        }
    }
}
