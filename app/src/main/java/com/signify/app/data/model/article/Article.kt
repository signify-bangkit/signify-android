package com.signify.app.data.model.article

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val title: String,
    val content: String,
    val date: String?,
    val imageUrl: String?,
): Parcelable