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
                Log.e(TAG, "Exception in getNewsHeadlines(): $e")
                newsHeadlines.postValue(ResourceState.Error("Something went wrong"))
            }
        }
    }
}