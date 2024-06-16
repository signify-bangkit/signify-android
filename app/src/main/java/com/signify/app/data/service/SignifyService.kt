package com.signify.app.data.service

import com.signify.app.data.auth.LoginRequest
import com.signify.app.data.auth.LoginResponse
import com.signify.app.data.auth.RegisterRequest
import com.signify.app.data.auth.RegisterResponse
import retrofit2.http.Body
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

//    @POST("api/translation/add-translation")
//    @POST("api/history")
//    @POST("api/auth/login")
}