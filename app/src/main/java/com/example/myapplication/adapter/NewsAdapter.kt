package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.NewsContent
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val newsList: ArrayList<NewsContent>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView) {
        fun bind(name: String) {
            textView.news_name.text = name
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