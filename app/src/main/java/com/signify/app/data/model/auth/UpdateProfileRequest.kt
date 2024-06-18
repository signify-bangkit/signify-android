package com.signify.app.data.model.auth

import retrofit2.http.Field

data class UpdateProfileRequest(
    @Field("firstName")
    val firstName: String,

    @Field("lastName")
    val lastName: String,
)
