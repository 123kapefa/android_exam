package com.example.test_app

import android.app.Application
import com.example.test_app.data.di.dataModule
import com.example.test_app.data.di.databaseModule
import com.example.test_app.data.di.networkModule
import com.example.test_app.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.example.test_app.presentation.di.presentationModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                domainModule,
                dataModule,
                networkModule,
                presentationModule,
                databaseModule
            )
        }
    }
}