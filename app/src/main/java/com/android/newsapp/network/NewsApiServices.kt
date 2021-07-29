package com.android.newsapp.network

import com.android.newsapp.models.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsApiServices {
    @GET("everything")
    suspend fun getData(@QueryMap queryMap: HashMap<String, String>): Response<NewsData>
}