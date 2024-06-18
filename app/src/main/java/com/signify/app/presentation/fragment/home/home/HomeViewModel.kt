package com.signify.app.presentation.fragment.home.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signify.app.data.model.article.ArticleResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class HomeViewModel(val repository: ArticleRepository) : ViewModel() {
    private val _results = MutableLiveData<ApiStatus<ArticleResponse>>()
    val results: LiveData<ApiStatus<ArticleResponse>> =
        _results

    fun getArticles() {
        viewModelScope.launch {
            repository.getArticles().collect {
                _results.value = it
            }
        }
    }

    init {
        getArticles()
    }
}