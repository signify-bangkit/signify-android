package com.signify.app.presentation.fragment.history.history_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.model.base.ApiResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryDetailViewModel(val repository: HistoryRepository) : ViewModel() {
    private val _deleteResult = MutableLiveData<ApiStatus<ApiResponse>>()
    val deleteResult: LiveData<ApiStatus<ApiResponse>> =
        _deleteResult


    fun deleteHistory(history: String) {
        viewModelScope.launch {
            repository.deleteHistory(history).collect {
                _deleteResult.value = it
            }
        }
    }
}