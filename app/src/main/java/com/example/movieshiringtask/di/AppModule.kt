package com.example.movieshiringtask.di

import androidx.room.Room
import com.example.movieshiringtask.businesslogic.*
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.koin.androidContext
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
            get<RoomDao>()
        )
    }

    single<RoomDB> {
        Room.databaseBuilder(androidContext(), RoomDB::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    single<RoomDao> {
        get<RoomDB>().roomDao()
    }

}