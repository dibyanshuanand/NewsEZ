package com.dibanand.newsez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.dibanand.newsez.databinding.ActivityHomeBinding
import com.dibanand.newsez.repository.NewsRepository
import com.dibanand.newsez.ui.NewsViewModel
import com.dibanand.newsez.ui.NewsViewModelProviderFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var newsViewModel: NewsViewModel

    companion object {
        const val TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val newsRepository = NewsRepository()
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        Log.e(TAG, "onCreate: Called")
        newsViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
    }
}