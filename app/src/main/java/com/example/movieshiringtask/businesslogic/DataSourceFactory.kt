package com.example.movieshiringtask.businesslogic

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieshiringtask.businesslogic.DataSource as customDataSource


class DataSourceFactory constructor(private val dataSource: customDataSource) :
    DataSource.Factory<Int, Search>() {

    private val movieDataSourceLiveData = MutableLiveData<customDataSource>()

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


    fun getDataFactoryLiveData(): MutableLiveData<customDataSource> {
        return movieDataSourceLiveData;
    }
}