package com.signify.app.data.model.history

import retrofit2.http.Field

class HistoryRequest(

    @Field("translatedText")
    val translatedText: String

)