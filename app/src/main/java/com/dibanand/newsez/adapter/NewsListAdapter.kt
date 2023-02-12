package com.dibanand.newsez.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dibanand.newsez.data.NewsItem
import com.dibanand.newsez.databinding.NewsListItemBinding

typealias OnNewsItemClickListener = (NewsItem) -> Unit

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {

    inner class NewsItemViewHolder(itemViewBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    companion object {
        const val TAG = "NewsListAdapter"
    }

    init {
        setHasStableIds(true)
    }

    private lateinit var newsItemBinding: NewsListItemBinding
    private val diffCallback = object : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.articleUrl == newItem.articleUrl
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }
    val listDiffer = AsyncListDiffer(this, diffCallback)
    private var onNewsItemClickListener: OnNewsItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        newsItemBinding = NewsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsItemViewHolder(newsItemBinding)
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = listDiffer.currentList[position]
        newsItemBinding.apply {
            if (!item.imageUrl.isNullOrBlank()) {
                Glide.with(holder.itemView).load(item.imageUrl).into(ivNewsImage)
            }
            if (item.source != null) {
                tvSource.text = item.source.name
            } else {
                tvSource.visibility = View.INVISIBLE
                tvBy.visibility = View.INVISIBLE
            }
            tvHeadline.text = item.title
            tvPublishTime.text = item.publishedAt
        }

        holder.itemView.setOnClickListener {
            onNewsItemClickListener?.invoke(item)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setOnAdapterItemClickListener(listener: OnNewsItemClickListener) {
        this.onNewsItemClickListener = listener
    }
}