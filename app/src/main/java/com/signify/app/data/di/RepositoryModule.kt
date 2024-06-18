package com.signify.app.data.di

import com.signify.app.data.repository.ArticleRepository
import com.signify.app.data.repository.ArticleRepositoryImpl
import com.signify.app.data.repository.AuthRepository
import com.signify.app.data.repository.AuthRepositoryImpl
import com.signify.app.data.repository.HistoryRepository
import com.signify.app.data.repository.HistoryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<HistoryRepository> { HistoryRepositoryImpl(get()) }
    single<ArticleRepository> { ArticleRepositoryImpl(get()) }
}