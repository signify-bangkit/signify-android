package com.raihan.story.data.model.dto.network

sealed class ApiStatus<out R> {
    data class Success<out T>(val data: T) : ApiStatus<T>()
    data class Error(val errorMessage: String) : ApiStatus<Nothing>()
    data object Loading : ApiStatus<Nothing>()
}
