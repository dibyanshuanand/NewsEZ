package com.dibanand.newsez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dibanand.newsez.adapter.NewsListAdapter
import com.dibanand.newsez.databinding.FragmentNewsBookmarksBinding
import com.dibanand.newsez.ui.NewsViewModel

class NewsBookmarksFragment : Fragment() {

    companion object {
        const val TAG = "NewsBookmarksFragment"
    }
    private lateinit var binding: FragmentNewsBookmarksBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNewsBookmarksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupUi()

        viewModel.getAllBookmarks().observe(viewLifecycleOwner) { bookmarks ->
            newsListAdapter.listDiffer.submitList(bookmarks)
        }
    }

    private fun setupUi() {
        setupRecyclerView()
        newsListAdapter.setOnAdapterItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("newsItem", it)
            }
            findNavController().navigate(
                R.id.action_newsBookmarksFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        newsListAdapter = NewsListAdapter()
        binding.rvNewsBookmarks.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}