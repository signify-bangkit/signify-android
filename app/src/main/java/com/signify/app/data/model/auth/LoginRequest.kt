package com.signify.app.data.model.auth

import retrofit2.http.Field

data class LoginRequest(

    @Field("email")
    val email: String,

    @Field("password")
    val password: String

)
