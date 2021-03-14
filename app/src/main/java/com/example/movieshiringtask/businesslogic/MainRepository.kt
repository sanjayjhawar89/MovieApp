package com.example.movieshiringtask.businesslogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

const val PAGED_LIST_PAGE_SIZE = 20

class MainRepository constructor(
    private val dataSourceFactory: DataSourceFactory
) {

    /**
     * Return Api status
     * 1. Loading
     * 2. Done
     * 3 Error
     */
    val status: LiveData<Status> = Transformations.switchMap(
        dataSourceFactory.getDataFactoryLiveData()
    ) {
        it.status
    }


    /*
    Method to fetch results from Remote server
     */
    fun fetchResults(query: String): LiveData<PagedList<Search>> {
        clear()
        dataSourceFactory.setMovieType(query)
        val config = PagedList.Config.Builder()
            .setPageSize(PAGED_LIST_PAGE_SIZE)
            .setInitialLoadSizeHint(PAGED_LIST_PAGE_SIZE * 3)
            .build()
        return LivePagedListBuilder<Int, Search>(dataSourceFactory, config).build()
    }

    fun clear() {
        dataSourceFactory.clear()
    }

    fun retry() {
        dataSourceFactory.getDataFactoryLiveData().value?.retry()
    }

}