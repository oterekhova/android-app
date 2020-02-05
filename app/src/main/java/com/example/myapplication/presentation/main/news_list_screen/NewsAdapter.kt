package com.example.myapplication.presentation.main.news_list_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.entity.NewsContent
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    //private var onClickListener: View.OnClickListener? = null
    private val newsList: MutableList<NewsContent> = ArrayList()

    fun setItems(newItems: List<NewsContent>) {
        newsList.clear()
        newsList.addAll(newItems)
        notifyDataSetChanged()
    }

//    fun setOnClickListener(l: View.OnClickListener) {
//        onClickListener = l
//    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(name: String) {
            //itemView.setOnClickListener(onClickListener)
            itemView.news_name.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_item, parent, false) as View

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position].text)
    }

    override fun getItemCount() = newsList.size

}
