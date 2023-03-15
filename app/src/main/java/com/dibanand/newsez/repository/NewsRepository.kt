package com.dibanand.newsez.repository

import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.data.NewsItem
import com.dibanand.newsez.db.NewsItemDao
import com.dibanand.newsez.network.NewsApiService
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsItemDao: NewsItemDao,
    private val newsApiService: NewsApiService
) {

    suspend fun getNewsHeadlines(pageNumber: Int): Response<NewsApiResponse> {
        return newsApiService.getTopHeadlines(pageNumber = pageNumber)
    }

    suspend fun saveItemToDb(newsItem: NewsItem): Long {
        return newsItemDao.upsert(newsItem)
    }

    fun getAllBookmarks() = newsItemDao.getAllBookmarks()

    suspend fun deleteBookmarkedItem(newsItem: NewsItem) {
        newsItemDao.deleteItem(newsItem)
    }
}