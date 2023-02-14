package com.dibanand.newsez.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.data.NewsItem
import com.dibanand.newsez.repository.NewsRepository
import com.dibanand.newsez.util.NetworkConnectionManager
import com.dibanand.newsez.util.ResourceState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
): AndroidViewModel(app) {

    val newsHeadlines: MutableLiveData<ResourceState<NewsApiResponse>> = MutableLiveData()
    private var newsHeadlinesResponse: NewsApiResponse? = null
    var currentPage: Int = 1
    val isLoadingActive: MutableLiveData<Boolean> = MutableLiveData(false)

    companion object {
        const val TAG = "NewsViewModel"
    }

    init {
        getNewsHeadlines()
    }

    fun getNewsHeadlines() {
        viewModelScope.launch {
            newsHeadlines.postValue(ResourceState.Loading())
            try {
                if (NetworkConnectionManager.isInternetAvailable(getApplication())) {
                    val response = newsRepository.getNewsHeadlines(currentPage)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            currentPage++
                            if (newsHeadlinesResponse == null) {
                                newsHeadlinesResponse = res
                            } else {
                                val oldArticles = newsHeadlinesResponse?.articles
                                val newArticles = res.articles
                                oldArticles?.addAll(newArticles)
                            }
                            newsHeadlines.postValue(
                                ResourceState.Success(newsHeadlinesResponse ?: res)
                            )
                        }
                    } else {
                        newsHeadlines.postValue(ResourceState.Error(response.message()))
                    }
                } else {
                    newsHeadlines.postValue(ResourceState.Blank(message = "No internet available"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getNewsHeadlines(): $e")
                newsHeadlines.postValue(ResourceState.Error("Something went wrong"))
            }
        }
    }

    fun bookmarkItem(newsItem: NewsItem): Job {
        return viewModelScope.launch {
            newsRepository.saveItemToDb(newsItem)
        }
    }

    fun getAllBookmarks() = newsRepository.getAllBookmarks()

    fun deleteBookmarkedItem(newsItem: NewsItem): Job {
        return viewModelScope.launch {
            newsRepository.deleteBookmarkedItem(newsItem)
        }
    }
}