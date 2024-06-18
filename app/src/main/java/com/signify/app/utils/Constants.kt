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
        content = """
           Signify is just a sign language translating app, it's that simple. Here's how you can maximize the potential of this app and use it fully:
           1. First of all, click the center circle button on the navigation bar.
           2. As you can see there is a camera preview, Show ur hands and let the app detect your gesture.
           3. The app will detect the gesture, this app uses the new SIBI model for detection.
           4. On other notes, this app will only detect per letter.
           5. When done, you can save the output to your history database.
           6. For other information, you could see the home page (eg, sign languages examples) and profile page.
        """.trimIndent(),
        date = "2023-06-18",
        imageUrl = "https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo"
    ),
)