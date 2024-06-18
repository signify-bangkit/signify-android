package com.signify.app.data.service

import com.signify.app.data.model.history.HistoryRequest
import com.signify.app.data.model.history.HistoryResponse
import com.signify.app.data.model.article.ArticleResponse
import com.signify.app.data.model.auth.LoginRequest
import com.signify.app.data.model.auth.LoginResponse
import com.signify.app.data.model.auth.RegisterRequest
import com.signify.app.data.model.auth.UpdateProfileRequest
import com.signify.app.data.model.auth.UpdateProfileResponse
import com.signify.app.data.model.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SignifyService {
    @POST("api/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): ApiResponse

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @PUT("api/profile/update")
    suspend fun updateProfile(
        @Body profile: UpdateProfileRequest
    ): UpdateProfileResponse

    @POST("api/translation/add-translation")
    suspend fun uploadHistory(
        @Body text: HistoryRequest
    ): ApiResponse

    @GET("api/history")
    suspend fun getHistories(): HistoryResponse

    @DELETE("api/history/delete")
    suspend fun deleteHistory(
        @Query("historyId") historyId: String
    ): ApiResponse

    @GET("api/articles")
    suspend fun getArticles(): ArticleResponse
}