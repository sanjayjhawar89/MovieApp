package com.example.movieshiringtask.businesslogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

sealed class Status {
    object Loading : Status()
    object Error : Status()
    object Done : Status()
}

class DataSource constructor(private val apiService: ApiService, private val compositeDisposable: CompositeDisposable) :
    PageKeyedDataSource<Int, Search>() {
    
    private var page = 1

    private lateinit var query: String

    fun setQuery(query: String) {
        this.query = query
    }

    private val statusInternal: MutableLiveData<Status> = MutableLiveData()

    val status: LiveData<Status>
        get() = statusInternal


    private var retryCompletable: Completable? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Search?>) {
        compositeDisposable.add(
            apiService.getRequest(query, page)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    updateState(Status.Loading)
                }
                .subscribe({ response ->
                    updateState(Status.Done)
                    callback.onResult(response.items, null, page + 1)
                }, { error ->
                    updateState(Status.Error)
                    setRetry(Action { loadInitial(params, callback) })
                    Timber.e(error, "Error Initial")
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Search?>) {
        page = params.key
        compositeDisposable.add(
            apiService.getRequest(query, page)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    updateState(Status.Loading)
                }
                .subscribe({ response ->
                    updateState(Status.Done)
                    callback.onResult(response.items, page + 1)
                }, { error ->
                    updateState(Status.Error)
                    setRetry(Action { loadAfter(params, callback) })
                    Timber.e(error, "Error After")
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Search>) {
    }

    fun clear() {
        compositeDisposable.clear()
        page = 1
    }

    private fun updateState(status: Status) {
        this.statusInternal.postValue(status)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}