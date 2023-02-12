package com.dibanand.newsez.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class NewsItem(
    var id: Int?,
    @SerializedName("source")
    val source: NewsSource?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val articleUrl: String?,
    @SerializedName("urlToImage")
    val imageUrl: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
) : Serializable {
    override fun hashCode(): Int {
        var result = id.hashCode()
        if (articleUrl.isNullOrEmpty()){
            result = 31 * result + articleUrl.hashCode()
        }
        return result
    }
}
