package com.dibanand.newsez

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.dibanand.newsez.databinding.FragmentNewsItemBinding
import com.dibanand.newsez.ui.NewsViewModel

class NewsItemFragment : Fragment() {

    companion object {
        const val TAG = "ArticleFragment"
    }

    private lateinit var binding: FragmentNewsItemBinding
    private lateinit var viewModel: NewsViewModel
    val args: NewsItemFragmentArgs by navArgs()

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
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        val item = args.newsItem
        binding.wvNewsArticle.apply {
            webViewClient = WebViewClient()
            item.articleUrl?.let { loadUrl(it) }
        }
    }
}