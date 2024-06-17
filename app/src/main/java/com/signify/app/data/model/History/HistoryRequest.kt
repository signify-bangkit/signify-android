package com.signify.app.data.model.History

import retrofit2.http.Field

class HistoryRequest(

    @Field("translatedText")
    val translatedText: String

)