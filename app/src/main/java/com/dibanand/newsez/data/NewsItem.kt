package com.dibanand.newsez.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class NewsItem(
    @SerializedName("author")
    val source: NewsSource = NewsSource(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("url")
    val articleUrl: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("publishedAt")
    val publishedAt: String = "",
) : Serializable
