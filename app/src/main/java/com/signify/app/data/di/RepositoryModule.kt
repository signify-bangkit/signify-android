package com.signify.app.data.di

import com.signify.app.data.auth.AuthRepository
import com.signify.app.data.auth.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}