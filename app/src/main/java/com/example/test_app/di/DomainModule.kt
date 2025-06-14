package com.example.test_app.di

import com.example.test_app.domain.usecase.GetAchievementByIdUseCase
import com.example.test_app.domain.usecase.GetPlayerAchievementsUseCase
import com.example.test_app.domain.usecase.GetPlayerInfoUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetPlayerAchievementsUseCase(get()) }
    factory { GetPlayerInfoUseCase(get()) }
    factory { GetAchievementByIdUseCase(get()) }
}