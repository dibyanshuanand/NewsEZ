package com.dibanand.newsez.repository

import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.data.NewsItem
import com.dibanand.newsez.db.NewsItemDatabase
import com.dibanand.newsez.network.RetrofitInstance
import retrofit2.Response

class NewsRepository(private val database: NewsItemDatabase) {

    suspend fun getNewsHeadlines(pageNumber: Int): Response<NewsApiResponse> {
        return RetrofitInstance.newsApiService.getTopHeadlines(pageNumber = pageNumber)
    }

    suspend fun saveItemToDb(newsItem: NewsItem): Long {
        return database.getNewsItemDao().upsert(newsItem)
    }

    fun getAllBookmarks() = database.getNewsItemDao().getAllBookmarks()

    suspend fun deleteBookmarkedItem(newsItem: NewsItem) {
        database.getNewsItemDao().deleteItem(newsItem)
    }
}