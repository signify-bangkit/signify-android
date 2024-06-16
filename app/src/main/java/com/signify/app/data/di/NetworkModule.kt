package com.signify.app.data.di

import com.signify.app.data.service.SignifyService
import com.signify.app.utils.BASE_URL
import com.signify.app.utils.HeaderInterceptor
import com.signify.app.utils.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single { PreferenceManager(get()) }
    single {
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor(get()))
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .build()
    }
    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(SignifyService::class.java)
    }

}

private fun headerInterceptor(preferenceManager: PreferenceManager): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"

    return HeaderInterceptor(headers, preferenceManager)
}
