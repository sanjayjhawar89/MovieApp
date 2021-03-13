package com.example.movieshiringtask.businesslogic

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


class DataSourceFactory constructor(private val dataSource: com.example.movieshiringtask.businesslogic.DataSource) :
    DataSource.Factory<Int, Search>() {

    private val movieDataSourceLiveData = MutableLiveData<com.example.movieshiringtask.businesslogic.DataSource>()

    fun setMovieType(movieType: String) {
        dataSource.setQuery(movieType)
    }


    fun clear() {
        dataSource.clear()
    }

    override fun create(): DataSource<Int, Search> {
        movieDataSourceLiveData.postValue(dataSource)
        return dataSource
    }


    fun getDataFactoryLiveData(): MutableLiveData<com.example.movieshiringtask.businesslogic.DataSource> {
        return movieDataSourceLiveData;
    }
}