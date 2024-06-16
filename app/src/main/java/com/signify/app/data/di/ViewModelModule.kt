package com.signify.app.data.di

import com.signify.app.presentation.fragment.login.LoginViewModel
import com.signify.app.presentation.fragment.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}