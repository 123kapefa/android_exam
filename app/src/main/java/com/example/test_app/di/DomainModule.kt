package com.example.test_app.di

import com.example.test_app.domain.usecase.GetAllItemsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllItemsUseCase(get()) }
}