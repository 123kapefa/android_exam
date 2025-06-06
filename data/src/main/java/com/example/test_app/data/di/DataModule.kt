package com.example.test_app.data.di

import com.example.test_app.data.repository.ItemRepositoryImpl
import com.example.test_app.domain.repository.ItemRepository

import org.koin.dsl.module

val dataModule = module {
    single<ItemRepository> { ItemRepositoryImpl() }

}