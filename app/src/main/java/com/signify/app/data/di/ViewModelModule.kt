package com.signify.app.data.di

import com.signify.app.presentation.fragment.analyze.output.OutputViewModel
import com.signify.app.presentation.fragment.auth.AuthViewModel
import com.signify.app.presentation.fragment.history.history.HistoryViewModel
import com.signify.app.presentation.fragment.history.history_detail.HistoryDetailViewModel
import com.signify.app.presentation.fragment.home.home.HomeViewModel
import com.signify.app.presentation.fragment.login.LoginViewModel
import com.signify.app.presentation.fragment.profile.profile.ProfileViewModel
import com.signify.app.presentation.fragment.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // auth
    viewModel { AuthViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }

    // article
    viewModel { HomeViewModel(get()) }

    // output / history
    viewModel { OutputViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { HistoryDetailViewModel(get()) }

    // profile
    viewModel { ProfileViewModel(get()) }
}