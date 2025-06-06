package com.example.test_app.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.test_app.presentation.viewmodel.ItemListViewModel

val appModule = module {
    viewModel { ItemListViewModel(get()) }
}