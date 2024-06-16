package com.signify.app.presentation.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.auth.AuthRepository
import com.signify.app.data.auth.LoginRequest
import com.signify.app.data.auth.LoginResponse
import com.signify.app.data.basemodel.ApiStatus
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
