package com.dibanand.newsez

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dibanand.newsez.databinding.ActivityHomeBinding
import com.dibanand.newsez.ui.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
//    private lateinit var newsViewModel: NewsViewModel
    private val newsViewModel: NewsViewModel by viewModels()

    companion object {
        const val TAG = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
//        newsViewModel =
//            ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsFrag) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavBar.setupWithNavController(navController)

        newsViewModel.isLoadingActive.observe(this) { isActive ->
            binding.loader.visibility = if (isActive) View.VISIBLE else View.GONE
        }
    }
}