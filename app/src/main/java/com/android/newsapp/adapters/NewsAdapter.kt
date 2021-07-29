package com.android.newsapp.adapters

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.newsapp.databinding.NewsRowBinding
import com.android.newsapp.models.Articles
import com.bumptech.glide.Glide

class NewsAdapter(private val context: Context, val onClickEvent: OnClickEvent): RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){
    private var items = ArrayList<Articles>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = NewsRowBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.newsTitleTextView.text = items[position].title
        holder.binding.newsDescription.text = items[position].description

        if (!(context as Activity).isFinishing){
            val url = Uri.parse(items[position].urlToImage)
            Glide.with(context)
                .load(url)
                .into(holder.binding.newsImageView)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newsList: ArrayList<Articles>){
        val diffUtil = DiffUtilCallback(items, newsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
        items = newsList
    }

    inner class MyViewHolder(val binding: NewsRowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onClickEvent.onNewsClick(items[adapterPosition].url)
            }
        }
    }

    interface OnClickEvent{
        fun onNewsClick(articleUrl: String)
    }

    class DiffUtilCallback(private val newList: ArrayList<Articles>, private val oldList: ArrayList<Articles>): DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].content == newList[newItemPosition].content
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when{
                oldList[oldItemPosition].title != newList[newItemPosition].title -> false

                oldList[oldItemPosition].content != newList[newItemPosition].content -> false

                oldList[oldItemPosition].description != newList[newItemPosition].description -> false

                oldList[oldItemPosition].url != newList[newItemPosition].url -> false

                oldList[oldItemPosition].urlToImage != newList[newItemPosition].urlToImage -> false

                else -> true
            }
        }
    }
}