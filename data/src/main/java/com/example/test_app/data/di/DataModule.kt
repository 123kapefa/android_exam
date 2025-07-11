package com.example.test_app.data.di

import com.example.test_app.data.repository.PlayerRepositoryImpl
import com.example.test_app.domain.repository.PlayerRepository
import org.koin.dsl.module

val dataModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl(get(), get(), get())
    }
}