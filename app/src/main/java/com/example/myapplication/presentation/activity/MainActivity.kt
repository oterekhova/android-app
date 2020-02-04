package com.example.myapplication.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.api.RetrofitApiClient
import com.example.myapplication.presentation.adapter.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter: NewsAdapter by lazy { NewsAdapter() }
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()
        initNewsRecyclerView()
        loadNewsData()
    }

    private fun initNewsRecyclerView() {
        news_list.layoutManager = LinearLayoutManager(this)
        news_list.adapter = adapter
    }

    private fun loadNewsData() {
        compositeDisposable?.add(
            RetrofitApiClient.create().getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        val sortedNews =
                            result.payload.sortedByDescending { it.publicationDate.milliseconds }
                        adapter.setItems(sortedNews)
                        adapter.notifyDataSetChanged()
                    },
                    { error -> error.printStackTrace() }
                )
        )
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
