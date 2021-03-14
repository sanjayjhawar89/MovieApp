package com.example.movieshiringtask.businesslogic

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(".")
    fun getRequest(
        @Query("s") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = "ab6f491c"
    ): Single<ApiResponse>
}
