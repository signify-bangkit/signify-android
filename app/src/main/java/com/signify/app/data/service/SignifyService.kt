package com.signify.app.data.service

import com.signify.app.data.model.history.HistoryRequest
import com.signify.app.data.model.history.HistoryResponse
import com.signify.app.data.model.article.ArticleResponse
import com.signify.app.data.model.auth.LoginRequest
import com.signify.app.data.model.auth.LoginResponse
import com.signify.app.data.model.auth.RegisterRequest
import com.signify.app.data.model.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface SignifyService {
    @POST("api/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): ApiResponse

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

//    @POST("api/profile/update")
//    suspend fun updateProfile(
//        @Body name: LoginRequest
//    ): LoginResponse

    @POST("api/translation/add-translation")
    suspend fun uploadHistory(
        @Body text: HistoryRequest
    ): ApiResponse

    @GET("api/history")
    suspend fun getHistories(): HistoryResponse

    @DELETE("api/history/delete")
    suspend fun deleteHistory(
        @Field("historyId") historyId: String
    ): HistoryResponse

    @GET("api/articles")
    suspend fun getArticles(): ArticleResponse
}