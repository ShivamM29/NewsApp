package com.android.newsapp.models

import retrofit2.http.Url

data class NewsData(
    var articles: Array<Articles>
)

data class Articles(
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var content: String
)
