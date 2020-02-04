package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.NewsContent

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList: MutableList<NewsContent> = ArrayList()

    fun setItems(newItems: List<NewsContent>) {
        newsList.clear()
        newsList.addAll(newItems)
        notifyDataSetChanged()
    }

    class NewsViewHolder(private val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(name: String) {
            textView.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false) as TextView

        return NewsViewHolder(textView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position].name)
    }

    override fun getItemCount() = newsList.size

}