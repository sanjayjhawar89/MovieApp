package com.example.movieshiringtask.di

import com.example.movieshiringtask.PresentationLogic.MainActivityVIewModel
import com.example.movieshiringtask.businesslogic.MainRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainActivityVIewModel(
            get<MainRepository>()
        )
    }

}