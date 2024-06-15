package com.signify.app.data

sealed class ApiStatus<out R> {
    data class Success<out T>(val data: T) : ApiStatus<T>()
    data class Error(val errorMessage: String) : ApiStatus<Nothing>()
    data object Loading : ApiStatus<Nothing>()
}
