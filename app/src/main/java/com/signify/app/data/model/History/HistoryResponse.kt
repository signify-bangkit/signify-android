package com.signify.app.data.model.History

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryResponse(

    @Json(name = "results")
    val results: List<History>
)

@JsonClass(generateAdapter = true)
data class CreatedAt(

    @Json(name = "_nanoseconds")
    val nanoseconds: Int,

    @Json(name = "_seconds")
    val seconds: Int
)

@JsonClass(generateAdapter = true)
data class History(

    @Json(name = "createdAt")
    val createdAt: CreatedAt,

    @Json(name = "translatedText")
    val translatedText: String,

    @Json(name = "email")
    val email: String
)
