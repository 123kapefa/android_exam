package com.example.test_app.data.di

import androidx.room.Room
import com.example.test_app.data.local.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "isaac.db"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }
    single { get<AppDatabase>().achievementDao() }
    single { get<AppDatabase>().playerDao() }
}