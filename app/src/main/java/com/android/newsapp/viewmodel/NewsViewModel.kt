package com.android.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.newsapp.models.NewsData
import com.android.newsapp.repositories.NewsRepo
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel: ViewModel() {
    private val newsRepo = NewsRepo()
    val mutableNewsList = MutableLiveData<Response<NewsData>>()

    fun getData(queryMap: HashMap<String, String>){
        viewModelScope.launch {
            mutableNewsList.value = newsRepo.getNews(queryMap)
        }
    }
}