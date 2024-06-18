package com.signify.app.data.model.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateProfileResponse(

    @Json(name = "msg")
    val msg: String,

    @Json(name = "error")
    val error: Boolean,

    @Json(name = "results")
    val results: Results? = null
)

@JsonClass(generateAdapter = true)
data class UpdatedAt(

    @Json(name = "_nanoseconds")
    val nanoseconds: Int? = null,

    @Json(name = "_seconds")
    val seconds: Int? = null
)

@JsonClass(generateAdapter = true)
data class CreatedAt(

    @Json(name = "_nanoseconds")
    val nanoseconds: Int? = null,

    @Json(name = "_seconds")
    val seconds: Int? = null
)

@JsonClass(generateAdapter = true)
data class Results(

    @Json(name = "createdAt")
    val createdAt: CreatedAt? = null,

    @Json(name = "firstName")
    val firstName: String? = null,

    @Json(name = "lastName")
    val lastName: String? = null,

    @Json(name = "password")
    val password: String? = null,

    @Json(name = "email")
    val email: String? = null,

    @Json(name = "updatedAt")
    val updatedAt: UpdatedAt? = null
)
