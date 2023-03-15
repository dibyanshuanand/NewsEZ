package com.dibanand.newsez.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dibanand.newsez.data.NewsItem

@Database(
    entities = [NewsItem::class],
    version = 1
)
@TypeConverters(DbTypeConverters::class)
abstract class NewsItemDatabase : RoomDatabase() {

    abstract fun getNewsItemDao(): NewsItemDao
}