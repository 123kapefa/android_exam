package com.example.test_app.presentation.di

import com.example.test_app.presentation.viewmodel.AchievementListViewModel
import com.example.test_app.presentation.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { AchievementListViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
}