package com.signify.app.data.service

import com.signify.app.data.model.History.HistoryRequest
import com.signify.app.data.model.History.HistoryResponse
import com.signify.app.data.model.auth.LoginRequest
import com.signify.app.data.model.auth.LoginResponse
import com.signify.app.data.model.auth.RegisterRequest
import com.signify.app.data.model.auth.RegisterResponse
import com.signify.app.data.model.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignifyService {
    @POST("api/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

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

//    @GET("api/article")
//    suspend fun getArticles(): ArticleResponse
}