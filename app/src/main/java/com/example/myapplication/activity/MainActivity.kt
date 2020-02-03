package com.example.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.data.NewsContent
import com.example.myapplication.services.RetrofitApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView
    private var compositeDisposable: CompositeDisposable? = null

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
        compositeDisposable?.add(
            RetrofitApiClient.create().getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result -> handleResponse(result.payload) },
                    { error -> error.printStackTrace() })
        )
    }

    private fun handleResponse(newsList: List<NewsContent>) {
        val newsArrayList = ArrayList(newsList)
        val newsAdapter = NewsAdapter(newsArrayList!!)
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
