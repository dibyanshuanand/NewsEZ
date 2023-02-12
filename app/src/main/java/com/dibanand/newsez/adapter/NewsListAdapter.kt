package com.dibanand.newsez.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dibanand.newsez.data.NewsItem
import com.dibanand.newsez.data.NewsSource
import com.dibanand.newsez.databinding.NewsListItemBinding

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {

    inner class NewsItemViewHolder(itemViewBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    companion object {
        const val TAG = "NewsListAdapter"
    }

    private lateinit var newsItemBinding: NewsListItemBinding
    var newsList: MutableList<NewsItem> = mutableListOf(
        NewsItem(
            source = NewsSource(name = "Livelaw.in"),
            title = "BREAKING: Supreme Court Collegium Recommends New Chief Justices For HCs Of Allahabad, Calcutta,... - Live Law - Indian Legal News",
            publishedAt = "2023-02-09 16:17",
            imageUrl = "https://www.livelaw.in/h-upload/2023/01/17/454220-supreme-court-of-india-sc-4.jpg"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        newsItemBinding = NewsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsItemViewHolder(newsItemBinding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = newsList[position]
        newsItemBinding.apply {
            Glide.with(holder.itemView).load(item.imageUrl).into(ivNewsImage)
            tvSource.text = item.source.name
            tvHeadline.text = item.title
            tvPublishTime.text = item.publishedAt
        }

        holder.itemView.setOnClickListener {
            Log.e(TAG, "onBindViewHolder: $item")
        }
    }


}