package com.dibanand.newsez

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dibanand.newsez.databinding.ActivityHomeBinding
import com.dibanand.newsez.db.NewsItemDatabase
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

        val newsRepository = NewsRepository(NewsItemDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        Log.e(TAG, "onCreate: Called")
        newsViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsFrag) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavBar.setupWithNavController(navController)

        newsViewModel.isLoadingActive.observe(this, Observer { isActive ->
            binding.loader.visibility = if(isActive) View.VISIBLE else View.GONE
        })
    }
}