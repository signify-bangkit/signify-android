package com.signify.app.utils

import com.signify.app.data.di.networkModule
import com.signify.app.data.di.repositoryModule
import com.signify.app.data.di.viewModelModule

val koinModules = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)

// set base url
const val BASE_URL = "http://192.168.0.114:8080/"