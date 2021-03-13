package com.example.movieshiringtask.di

import com.example.movieshiringtask.businesslogic.ApiService
import com.example.movieshiringtask.businesslogic.DataSource
import com.example.movieshiringtask.businesslogic.DataSourceFactory
import com.example.movieshiringtask.businesslogic.MainRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val appModule = module {

    single { CompositeDisposable() }

    single<DataSource> {
        DataSource(
            get<ApiService>(),
            get<CompositeDisposable>()
        )
    }

    single<DataSourceFactory> {
        DataSourceFactory(
            get<DataSource>()
        )
    }

    single<MainRepository> {
        MainRepository(
            get<DataSourceFactory>(),
            get<CompositeDisposable>()
        )
    }

}