package com.example.myapplication.presentation.main.news_list_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.entity.NewsDetails
import kotlinx.android.synthetic.main.item_news_list.view.*

class NewsAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList: MutableList<NewsDetails> = ArrayList()

    fun setItems(newItems: List<NewsDetails>) {
        newsList.clear()
        newsList.addAll(newItems)
        notifyDataSetChanged()
    }

    val items: ArrayList<NewsDetails> get() = ArrayList(newsList)

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(newsDetails: NewsDetails) {
            itemView.setOnClickListener { listener.invoke(newsDetails.id) }
            itemView.news_name.text = newsDetails.text
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news_list, parent, false) as View

        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

}
