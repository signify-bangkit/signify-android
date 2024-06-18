package com.signify.app.presentation.fragment.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.model.auth.UpdateProfileRequest
import com.signify.app.data.model.auth.UpdateProfileResponse
import com.signify.app.data.model.base.ApiResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.model.history.HistoryRequest
import com.signify.app.data.repository.AuthRepository
import kotlinx.coroutines.launch

class EditProfileViewModel(val repository: AuthRepository) : ViewModel() {
    private val _editResults = MutableLiveData<ApiStatus<UpdateProfileResponse>>()
    val editResults: LiveData<ApiStatus<UpdateProfileResponse>> =
        _editResults

    fun updateProfile(profile: UpdateProfileRequest) {

        viewModelScope.launch {
            repository.updateProfile(profile).collect {
                _editResults.value = it
            }
        }
    }
}