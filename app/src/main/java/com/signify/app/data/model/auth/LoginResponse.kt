package com.signify.app.data.model.auth

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class LoginResponse(

	@Json(name="user_name")
	val userName: String,

	@Json(name="token")
	val token: String
)
