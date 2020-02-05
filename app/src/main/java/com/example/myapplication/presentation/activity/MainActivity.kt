package com.example.myapplication.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.api.RetrofitApiClient
import com.example.myapplication.presentation.main.news_list_screen.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_main.*


class MainActivity : AppCompatActivity() {

    private val adapter: NewsAdapter by lazy { NewsAdapter() }
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_main)
        initNewsRecyclerView()
        loadNewsData()
    }

    private fun initNewsRecyclerView() {
        news_list.layoutManager = LinearLayoutManager(this)
        news_list.adapter = adapter
        //setOnClickListener()
    }

    private fun loadNewsData() {
        disposable = RetrofitApiClient.create().getNews()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { result ->
                    val sortedNews =
                        result.payload.sortedByDescending { it.publicationDate.milliseconds }
                    adapter.setItems(sortedNews)
                },
                { error -> error.printStackTrace() }
            )
    }

//    private fun setOnClickListener() {
//        adapter.setOnClickListener(View.OnClickListener { print("message") })
//    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
