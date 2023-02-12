package com.dibanand.newsez.network

import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.util.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val okHttpClient = OkHttpClient.Builder()
                .build()
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

        val newsApi: NewsApiService by lazy {
            retrofit.create(NewsApiService::class.java)
        }
    }
}