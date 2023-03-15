package com.dibanand.newsez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dibanand.newsez.databinding.FragmentNewsItemBinding
import com.dibanand.newsez.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsItemFragment : Fragment() {

    companion object {
        const val TAG = "ArticleFragment"
    }

    private lateinit var binding: FragmentNewsItemBinding
//    private lateinit var viewModel: NewsViewModel
    private val viewModel: NewsViewModel by viewModels()
    private val args: NewsItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentNewsItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        val item = args.newsItem
        binding.wvNewsArticle.apply {
            webViewClient = WebViewClient()
            item.articleUrl?.let { loadUrl(it) }
        }

        binding.fabBookmark.setOnClickListener {
            viewModel.bookmarkItem(item)
            Snackbar.make(view, "Saved to Bookmarks !", Snackbar.LENGTH_SHORT).show()
        }
    }
}