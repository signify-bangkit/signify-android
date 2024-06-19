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
const val BASE_URL = "https://capstone-project-signify.et.r.appspot.com/"

fun reloadKoinModules() {
    unloadKoinModules(koinModules)
    loadKoinModules(koinModules)
}

val dummyData: List<Article> = listOf(
    Article(
        title = "A Brief Introduction to Sign Language",
        content = "Sign language is a visual means of communicating using gestures, facial expressions, and body language. It is used predominantly by Deaf and hard of hearing individuals to communicate effectively. This article explores the history, types, and benefits of sign language, as well as its importance in fostering inclusivity and breaking down communication barriers.",
        date = "2023-06-18",
        imageUrl = "https://s3-alpha-sig.figma.com/img/25b7/3da7/4e344d3da334bf81c655014f3ff16970?Expires=1719792000&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=CR0TMZzVLegW6xaVZODi-LjaciznaYhvghJuEFrO8VCh32C3swxNCMT6KhkTnS-bmZ5xi7IzF82jouSzbyNVcXYrR2w7vAwUuVlw2xJVSx1~bv~IKt5clFvc5~Y2YsqNxzWrZHqhDHILvvtMSf~D~6uXpXzaxSPdv81dSWd~0Hso6Qc0OLjYJaJaqhsMu9IC5l8TL2FuMBd6XIdE2n-8Au0IKkD2gy7x2YExqm3Z1AHlssKaOVC5c3JKBPN5hKD47-h4Iz0YrW3PrAcmNHB2Qi7BUMKRshU9Rh9lpnIvMWXrOWBQCm6W5fT2mVfC~7C2AgrJWXwLPeOVwh551XllQQ__"
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