package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.services.RetrofitApiClient

class MainActivity : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNewsRecyclerView()
        loadNewsData()
    }

    private fun initNewsRecyclerView() {
        newsRecyclerView = findViewById(R.id.news_list)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadNewsData() {
        val newsList = RetrofitApiClient.create().getNews().execute().body()!!.payload
        val newsAdapter = NewsAdapter(newsList)
        newsRecyclerView.adapter = newsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
