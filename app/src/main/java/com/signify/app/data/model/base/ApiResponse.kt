package com.signify.app.data.model.base

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class ApiResponse(

	@Json(name="msg")
	val msg: String,

	@Json(name="error")
	val error: Boolean
)
