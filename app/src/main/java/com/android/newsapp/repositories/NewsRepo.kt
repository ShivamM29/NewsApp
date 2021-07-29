package com.android.newsapp.repositories

import com.android.newsapp.models.NewsData
import com.android.newsapp.network.ServiceBuilder
import retrofit2.Response

class NewsRepo {
    suspend fun getNews(queryMap: HashMap<String, String>) : Response<NewsData> = ServiceBuilder.newsApi!!.getData(queryMap)
}