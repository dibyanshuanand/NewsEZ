package com.dibanand.newsez.data

import androidx.annotation.Keep

@Keep
data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<NewsItem>,
)
