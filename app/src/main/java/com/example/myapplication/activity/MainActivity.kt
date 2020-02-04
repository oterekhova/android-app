package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.services.RetrofitApiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val adapter: NewsAdapter by lazy { NewsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNewsRecyclerView()
        loadNewsData()
    }

    private fun initNewsRecyclerView() {
        news_list.layoutManager = LinearLayoutManager(this)
        news_list.adapter = adapter
    }

    private fun loadNewsData() {
        thread {
            val newsList = RetrofitApiClient.create().getNews().execute().body()!!.payload
            news_list.post {
                adapter.setItems(newsList)
                adapter.notifyDataSetChanged()
            }
        }
    }

}
