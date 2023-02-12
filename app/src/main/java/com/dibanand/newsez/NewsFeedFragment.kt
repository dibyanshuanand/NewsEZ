package com.dibanand.newsez

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dibanand.newsez.adapter.NewsListAdapter
import com.dibanand.newsez.databinding.FragmentNewsFeedBinding
import com.dibanand.newsez.ui.NewsViewModel
import com.dibanand.newsez.util.ResourceState
import com.google.android.material.snackbar.Snackbar

class NewsFeedFragment : Fragment() {

    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    companion object {
        const val TAG = "NewsFeedFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNewsFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG, "onViewCreated: Called")
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupRecyclerView()

        viewModel.newsHeadlines.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ResourceState.Success -> {
                    response.data?.let { res ->
                        newsListAdapter.newsList = res.articles.toMutableList()
                    }
                }
                is ResourceState.Loading -> {
                    Snackbar.make(binding.rvNewsHeadlines, "Loading", Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResourceState.Error -> {
                    response.message?.let {msg ->
                        Snackbar.make(binding.rvNewsHeadlines, "Error: $msg", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                is ResourceState.Undefined -> {
                    // no-op
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsListAdapter = NewsListAdapter()
        binding.rvNewsHeadlines.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
            // TODO: Handle pagination - scrolling
        }
    }
}