package com.dibanand.newsez.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        @Volatile
        private var dbInstance: NewsItemDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = dbInstance ?: synchronized(lock) {
            dbInstance ?: createDatabase(context).also { dbInstance = it }
        }

        private fun createDatabase(context: Context): NewsItemDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NewsItemDatabase::class.java,
                "newsez_db.db"
            ).build()
        }
    }
}