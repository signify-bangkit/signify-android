package com.signify.app.data.repository

import com.signify.app.data.model.article.ArticleResponse
import com.signify.app.data.model.base.ApiStatus
import com.signify.app.data.service.SignifyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ArticleRepository {
    fun getArticles(): Flow<ApiStatus<ArticleResponse>>
}

class ArticleRepositoryImpl(private val api: SignifyService): ArticleRepository {
    override fun getArticles(): Flow<ApiStatus<ArticleResponse>> = flow {
        try {
            emit(ApiStatus.Loading)
            val response = api.getArticles()
            emit(ApiStatus.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiStatus.Error(e.message.toString()))
        }
    }
}

