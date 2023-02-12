package com.dibanand.newsez.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dibanand.newsez.data.NewsItem

@Dao
interface NewsItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(newsItem: NewsItem): Long

    @Query("SELECT * FROM newsez")
    fun getAllBookmarks(): LiveData<List<NewsItem>>

    @Delete
    suspend fun deleteItem(newsItem: NewsItem)
}