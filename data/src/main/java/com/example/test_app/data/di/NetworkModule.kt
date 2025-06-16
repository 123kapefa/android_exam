package com.example.test_app.data.di

import com.example.test_app.data.BuildConfig
import com.example.test_app.data.remote.PlayerRemoteDataSource
import com.example.test_app.data.remote.api.SteamApi
import com.example.test_app.data.remote.impl.PlayerRemoteDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.steampowered.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(SteamApi::class.java) }

    single<PlayerRemoteDataSource> {
        PlayerRemoteDataSourceImpl(
            api = get(),
            apiKey = BuildConfig.STEAM_API_KEY
        )
    }
}