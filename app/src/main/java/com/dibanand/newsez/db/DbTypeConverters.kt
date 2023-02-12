package com.dibanand.newsez.db

import androidx.room.TypeConverter
import com.dibanand.newsez.data.NewsSource

class DbTypeConverters {

    @TypeConverter
    fun fromNewsSource(source: NewsSource): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): NewsSource {
        return NewsSource(name, name)
    }
}