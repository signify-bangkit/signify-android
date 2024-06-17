package com.signify.app.presentation.fragment.history.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.model.History.HistoryResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) :
    ViewModel() {
    private val _results = MutableLiveData<ApiStatus<HistoryResponse>>()
    val results: LiveData<ApiStatus<HistoryResponse>> =
        _results

    fun getHistories() {
        viewModelScope.launch {
            repository.getHistories().collect {
                _results.value = it
            }
        }
    }
}