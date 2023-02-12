package com.dibanand.newsez.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dibanand.newsez.data.NewsApiResponse
import com.dibanand.newsez.repository.NewsRepository
import com.dibanand.newsez.util.ResourceState
import kotlinx.coroutines.launch

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
): AndroidViewModel(app) {

    val newsHeadlines: MutableLiveData<ResourceState<NewsApiResponse>> = MutableLiveData()
    var newsHeadlinesResponse: NewsApiResponse? = null
    var currentPage: Int = 1

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
                // TODO: Handle network states
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
            } catch (e: Exception) {
                Log.e(TAG, "Exception in getNewsHeadlines(): $e")
                newsHeadlines.postValue(ResourceState.Error("Something went wrong"))
            }
        }
    }
}