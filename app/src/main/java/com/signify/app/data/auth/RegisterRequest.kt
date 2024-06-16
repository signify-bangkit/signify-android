package com.signify.app.data.auth

import retrofit2.http.Field

data class RegisterRequest(
    @Field("firstName")
    val firstName: String,

    @Field("lastName")
    val lastName: String,

    @Field("email")
    val email: String,

    @Field("password")
    val password: String
)