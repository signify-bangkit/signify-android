package com.signify.app.presentation.fragment.analyze.output

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.model.History.HistoryRequest
import com.signify.app.data.model.base.ApiResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class OutputViewModel(private val repository: HistoryRepository) : ViewModel() {
    private val _uploadResult = MutableLiveData<ApiStatus<ApiResponse>>()
    val uploadResult: LiveData<ApiStatus<ApiResponse>> =
        _uploadResult

    fun uploadText(history: HistoryRequest) {

        viewModelScope.launch {
            repository.uploadHistory(history).collect {
                _uploadResult.value = it
            }
        }
    }
}