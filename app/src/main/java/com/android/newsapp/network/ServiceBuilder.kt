package com.android.newsapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val url by lazy {"https://newsapi.org/v2/"}

//    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
//    private val httpClient = OkHttpClient.Builder()
//        .addInterceptor(logger)
//        .build()

    private val newsBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsApi = newsBuilder?.create(NewsApiServices::class.java)
}