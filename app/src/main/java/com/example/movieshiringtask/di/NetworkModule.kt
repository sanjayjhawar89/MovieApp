package com.example.movieshiringtask.di

import com.example.movieshiringtask.businesslogic.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Module which provide network related objects (This also lies in app scope)
 */
val networkModule = module {
    single {
        val okHttpClient =
            createOkHttpClient()
        val retrofit = createRetrofit(
            okHttpClient
        )
        return@single retrofit.create<ApiService>(
            ApiService::class.java)
    }
}


private fun createOkHttpClient(): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(logging)
    }
    return clientBuilder.build()
}

private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
}
