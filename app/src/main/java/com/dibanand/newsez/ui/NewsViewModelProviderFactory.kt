package com.dibanand.newsez.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dibanand.newsez.repository.NewsRepository

class NewsViewModelProviderFactory(
    val app: Application,
    val repository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, repository) as T
    }
}