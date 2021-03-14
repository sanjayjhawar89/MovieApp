package com.example.movieshiringtask.di

import com.example.movieshiringtask.PresentationLogic.MovieListFragmentVIewModel
import com.example.movieshiringtask.businesslogic.MainRepository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MovieListFragmentVIewModel(
            get<MainRepository>()
        )
    }

}