package com.example.movieshiringtask.PresentationLogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.movieshiringtask.businesslogic.MainRepository
import com.example.movieshiringtask.businesslogic.Search

class MovieListFragmentVIewModel constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val status = mainRepository.status

    private val fetchData = MutableLiveData<String>()

    private val movieListInternal: LiveData<PagedList<Search>> =
        Transformations.switchMap(fetchData) {
            mainRepository.fetchResults(it)
        }

    val movieList: LiveData<PagedList<Search>>
        get() = movieListInternal

    /**
     * Method to  load the required repos  requests
     */
    fun loadApiData(query: String?) {
        if (query != null && query.isNotEmpty()) {
            fetchData.value = query
        }
    }

    fun retry() {
        mainRepository.retry()
    }
}