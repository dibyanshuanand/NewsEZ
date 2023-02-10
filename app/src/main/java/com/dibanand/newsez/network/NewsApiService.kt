package com.dibanand.newsez.network

import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        countryCode: String = "in",
        @Query("apiKey")
        apiKey: String = Constants.API_KEY
    ): Response<NewsApiResponse>
}