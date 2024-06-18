package com.signify.app.data.model.article

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Json

@JsonClass(generateAdapter = true)
data class ArticleResponse(

	@Json(name="results")
	val results: List<ArticleResponseItem>
)

@JsonClass(generateAdapter = true)
data class CreatedAt(

	@Json(name="_nanoseconds")
	val nanoseconds: Int,

	@Json(name="_seconds")
	val seconds: Int
)

@JsonClass(generateAdapter = true)
data class ArticleResponseItem(

	@Json(name="createdAt")
	val createdAt: CreatedAt,

	@Json(name="id")
	val id: String,

	@Json(name="title")
	val title: String,

	@Json(name="content")
	val content: String,

	val imageUrl: String?,
)
