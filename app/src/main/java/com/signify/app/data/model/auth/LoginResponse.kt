package com.signify.app.data.model.auth

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class LoginResponse(

	@Json(name="firstName")
	val firstName: String,

	@Json(name="lastName")
	val lastName: String,

	@Json(name="email")
	val email: String,

	@Json(name="token")
	val token: String
)
