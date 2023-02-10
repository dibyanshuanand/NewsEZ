package com.dibanand.newsez.data

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: MutableList<NewsItem>,
)
