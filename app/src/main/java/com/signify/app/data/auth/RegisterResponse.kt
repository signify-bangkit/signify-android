package com.signify.app.data.auth

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class RegisterResponse(

	@Json(name="msg")
	val msg: String
)