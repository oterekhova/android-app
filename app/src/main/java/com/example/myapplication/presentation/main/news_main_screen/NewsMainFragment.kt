package com.example.myapplication.presentation.main.news_main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.api.RetrofitApiClient
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsDetails
import com.example.myapplication.presentation.activity.MainActivity
import com.example.myapplication.presentation.main.news_list_screen.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_main.*


class NewsMainFragment : Fragment() {

    private val ARRAY_KEY = "serializable_array_key"
    private val adapter: NewsAdapter by lazy { NewsAdapter { newsId -> showNews(newsId) } }
    private var disposable: CompositeDisposable? = null

    companion object {
        fun newInstance(): NewsMainFragment {
            return NewsMainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_main, container, false)
        if (savedInstanceState != null) {
            restore(savedInstanceState)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        disposable = CompositeDisposable()
        initNewsRecyclerView()
        if (savedInstanceState != null) {
            restore(savedInstanceState)
        } else {
            loadNewsData()
        }
    }

    private fun initNewsRecyclerView() {
        news_list.layoutManager = LinearLayoutManager(requireActivity())
        news_list.adapter = adapter
    }

    private fun showNews(id: String) {
        (requireActivity() as MainActivity).displaySelectedNews(id)
    }

    private fun loadNewsData() {
        disposable?.add(
            RetrofitApiClient.create().getNews()
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

    private fun restore(savedInstanceState: Bundle) {
        if (savedInstanceState.getSerializable(ARRAY_KEY) != null) {
            val savedNewsList =
                savedInstanceState.getSerializable(ARRAY_KEY) as ArrayList<NewsDetails>
            adapter.setItems(savedNewsList)
        }
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
