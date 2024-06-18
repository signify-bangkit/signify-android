package com.signify.app.utils

import com.signify.app.data.di.networkModule
import com.signify.app.data.di.repositoryModule
import com.signify.app.data.di.viewModelModule
import com.signify.app.data.model.article.Article
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

val koinModules = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)

// set base url
const val BASE_URL = "http://192.168.0.114:8080/"

fun reloadKoinModules() {
    unloadKoinModules(koinModules)
    loadKoinModules(koinModules)
}

val dummyData: List<Article> = listOf(
    Article(
        title = "A Brief Introduction to Sign Language",
        content = "Sign language is a visual means of communicating using gestures, facial expressions, and body language. It is used predominantly by Deaf and hard of hearing individuals to communicate effectively. This article explores the history, types, and benefits of sign language, as well as its importance in fostering inclusivity and breaking down communication barriers.",
        date = "2023-06-18",
        imageUrl = "https://logos-world.net/wp-content/uploads/2023/08/ASL-Alphabet-500x281.png"
    ),
    Article(
        title = "How to use Signify",
        content = "",
        date = "2023-06-18",
        imageUrl = "https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo"
    ),
)