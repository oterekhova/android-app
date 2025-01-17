package com.example.myapplication.presentation.main.news_main_screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.Dependencies
import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsDetails
import com.example.myapplication.presentation.activity.MainActivity
import com.example.myapplication.presentation.main.news_list_screen.NewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_news_list.*


class NewsMainFragment : Fragment() {

    private val adapter: NewsAdapter by lazy { NewsAdapter { newsId -> showNews(newsId) } }
    private var disposable: CompositeDisposable? = null

    companion object {
        const val ARRAY_KEY = "serializable_array_key"

        fun newInstance(): NewsMainFragment {
            return NewsMainFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        disposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (view != null) {
            view
        } else {
            inflater.inflate(R.layout.fragment_news_list, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNewsRecyclerView()
        createSwipeLayout()
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
            Dependencies.newsApi.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { swipe_container.isRefreshing = true }
                .subscribe(
                    { result ->
                        sortAndSet(result)
                        swipe_container.isRefreshing = false
                    },
                    { error ->
                        error.printStackTrace()
                        swipe_container.isRefreshing = false
                    }
                )
        )
    }

    private fun sortAndSet(news: News) {
        val sortedList = news.payload.sortedByDescending { it.publicationDate.milliseconds }
        adapter.setItems(sortedList)
    }

    private fun restore(savedInstanceState: Bundle) {
        val savedNewsList = savedInstanceState.getSerializable(ARRAY_KEY) as? ArrayList<NewsDetails>
        savedNewsList?.let {
            adapter.setItems(savedNewsList)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(ARRAY_KEY, adapter.items)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun createSwipeLayout() {
        swipe_container.setOnRefreshListener {
            loadNewsData()
        }
        swipe_container.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}
