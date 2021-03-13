package com.example.movieshiringtask

import android.app.Application
import com.example.movieshiringtask.di.appModule
import com.example.movieshiringtask.di.networkModule
import com.example.movieshiringtask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    viewModelModule
                )
            )
        }
        Timber.plant(Timber.DebugTree())
    }
}