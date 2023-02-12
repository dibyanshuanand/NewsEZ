package com.dibanand.newsez.repository

import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.network.RetrofitInstance
import retrofit2.Response

class NewsRepository {

    suspend fun getNewsHeadlines(): Response<NewsApiResponse> {
        return RetrofitInstance.newsApi.getTopHeadlines()
    }
}