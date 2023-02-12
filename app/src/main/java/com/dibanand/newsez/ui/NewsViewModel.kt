package com.dibanand.newsez.ui

import android.app.Application
import androidx.lifecycle.*
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

    init {
        getNewsHeadlines()
    }

    fun getNewsHeadlines() {
        viewModelScope.launch {
            newsHeadlines.postValue(ResourceState.Loading())
            try {
                // TODO: Handle network states
                val response = newsRepository.getNewsHeadlines()
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        if (newsHeadlinesResponse == null) {
                            newsHeadlinesResponse = res
                        } else {
                            // TODO: Add support for pages
                        }
                        newsHeadlines.postValue(ResourceState.Success(res))
                    }
                } else {
                    newsHeadlines.postValue(ResourceState.Error(response.message()))
                }
            } catch (e: Exception) {
                newsHeadlines.postValue(ResourceState.Error("Something went wrong"))
            }
        }
    }
}