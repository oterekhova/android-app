package com.example.myapplication.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.api.RetrofitApiClient
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsContent
import com.example.myapplication.presentation.main.news_list_screen.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_main.*


class MainActivity : AppCompatActivity() {

    private val adapter: NewsAdapter by lazy { NewsAdapter() }
    private var disposable: CompositeDisposable? = null
    private var ARRAY_KEY = "array_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_main)
        disposable = CompositeDisposable()
        initNewsRecyclerView()

        if (savedInstanceState != null) {
            restore(savedInstanceState)
        } else {
            loadNewsData()
        }

    }

    private fun initNewsRecyclerView() {
        news_list.layoutManager = LinearLayoutManager(this)
        news_list.adapter = adapter
        setOnClickListener()
    }

    private fun loadNewsData() {
        disposable?.add(RetrofitApiClient.create().getNews()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result -> setSortedNewsList(result) },
                { error -> error.printStackTrace() }
            )
        )
    }

    private fun setSortedNewsList(news: News) {
        val sortedList = news.payload.sortedByDescending { it.publicationDate.milliseconds }
        adapter.setItems(sortedList)
    }

    private fun setOnClickListener() {
        adapter.setOnClickListener(View.OnClickListener {
            // TODO добавить реализацию
        })
        adapter.notifyDataSetChanged()
    }

    private fun restore(savedInstanceState: Bundle) {
        if (savedInstanceState.getSerializable(ARRAY_KEY) != null) {
            val savedNewsList = savedInstanceState.getSerializable(ARRAY_KEY) as ArrayList<NewsContent>
            adapter.setItems(savedNewsList)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restore(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(ARRAY_KEY, adapter.getItems())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
