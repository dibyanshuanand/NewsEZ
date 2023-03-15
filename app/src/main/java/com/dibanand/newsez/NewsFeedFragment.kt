package com.dibanand.newsez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dibanand.newsez.adapter.NewsListAdapter
import com.dibanand.newsez.databinding.FragmentNewsFeedBinding
import com.dibanand.newsez.ui.NewsViewModel
import com.dibanand.newsez.util.Constants.ITEMS_PER_PAGE
import com.dibanand.newsez.util.ResourceState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    companion object {
        const val TAG = "NewsFeedFragment"
    }
    private lateinit var binding: FragmentNewsFeedBinding
//    private lateinit var viewModel: NewsViewModel
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsListAdapter: NewsListAdapter
    private var isLastPage = false
    private var isScrolling = false
    private var isError = false
    private var isLoading = false
    private val onScrollListener: RecyclerView.OnScrollListener = object :
        RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val rvLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstItem = rvLayoutManager.findFirstVisibleItemPosition()
            val itemCount = rvLayoutManager.itemCount

            val isLastItem = (firstItem + rvLayoutManager.childCount) >= itemCount
            val isNotFirstItem = firstItem >= 0
            val isMoreItemsInList = itemCount >= ITEMS_PER_PAGE
            val paginate = !isError && !isLoading && !isLastPage && isScrolling && isLastItem &&
                    isNotFirstItem && isMoreItemsInList
            if (paginate) {
                viewModel.getNewsHeadlines()
                isScrolling = false
            }
        }
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
//        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupUi()
        setupObservers()
    }

    private fun setupUi() {
        setupRecyclerView()

        newsListAdapter.setOnAdapterItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("newsItem", it)
            }
            findNavController().navigate(R.id.action_newsFeedFragment_to_articleFragment, bundle)
        }

        binding.btnRetry.setOnClickListener { viewModel.getNewsHeadlines() }
        newsListAdapter.setDeleteBtnVisible(isVisible = false)
    }

    private fun setupObservers() {
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ResourceState.Success -> {
                    binding.grpConn.visibility = View.GONE
                    isError = false
                    isCurrentlyLoading(false)
                    response.data?.let { res ->
                        newsListAdapter.listDiffer.submitList(res.articles.toList())
                        val totalPages = res.totalResults / ITEMS_PER_PAGE + 2
                        isLastPage = viewModel.currentPage == totalPages
                    }
                }
                is ResourceState.Loading -> {
                    isCurrentlyLoading(true)
                }
                is ResourceState.Error -> {
                    isError = true
                    isCurrentlyLoading(false)
                    response.message?.let { msg ->
                        Snackbar.make(binding.rvNewsHeadlines, "Error: $msg", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
                is ResourceState.Blank -> {
                    isError = true
                    isCurrentlyLoading(false)
                    binding.grpConn.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun isCurrentlyLoading(active: Boolean) {
        isLoading = active
        viewModel.isLoadingActive.postValue(active)
    }

    private fun setupRecyclerView() {
        newsListAdapter = NewsListAdapter()
        binding.rvNewsHeadlines.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollListener)
        }
    }
}