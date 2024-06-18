package com.signify.app.data.repository

import com.signify.app.data.model.history.HistoryRequest
import com.signify.app.data.model.history.HistoryResponse
import com.signify.app.data.model.base.ApiResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.service.SignifyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface HistoryRepository {
    fun getHistories(): Flow<ApiStatus<HistoryResponse>>
    fun uploadHistory(history: HistoryRequest): Flow<ApiStatus<ApiResponse>>
}

class HistoryRepositoryImpl(private val api: SignifyService) :
    HistoryRepository {
    override fun getHistories(): Flow<ApiStatus<HistoryResponse>> = flow {
        try {
            emit(ApiStatus.Loading)
            val response = api.getHistories()
            emit(ApiStatus.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiStatus.Error(e.message.toString()))
        }
    }

    override fun uploadHistory(history: HistoryRequest): Flow<ApiStatus<ApiResponse>> =
        flow {
            try {
                emit(ApiStatus.Loading)
                val response = api.uploadHistory(history)
                emit(ApiStatus.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiStatus.Error(e.message.toString()))
            }
        }
}

