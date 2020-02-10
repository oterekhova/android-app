package com.example.myapplication.presentation.main.news_content_screen

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.data.Dependencies
import com.example.myapplication.data.entity.NewsContent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_content.*
import kotlinx.android.synthetic.main.news_content.view.*


class NewsContentFragment : Fragment() {

    private var disposable: CompositeDisposable? = null

    companion object {

        const val CONTENT_KEY = "serializable_content_key"
        const val NEWS_ID = "news_id"

        fun newInstance(content: String): NewsContentFragment {
            val newsContentFragment = NewsContentFragment()
            val args = Bundle()
            args.putString(NEWS_ID, content)
            newsContentFragment.arguments = args
            return newsContentFragment
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
            inflater.inflate(R.layout.news_content, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            restore(savedInstanceState)
        } else {
            displayNewsContent(view.content)
        }
    }

    private fun displayNewsContent(textView: TextView) {
        arguments?.getString(NEWS_ID)?.let {
            loadNewsContent(it)
        }
    }

    private fun loadNewsContent(id: String) {
        disposable?.add(
            Dependencies.newsApi.getNewsContent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> setContentText(result) },
                    { error -> error.printStackTrace() }
                )
        )
    }

    private fun setContentText(newsContent: NewsContent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.text = Html.fromHtml(
                newsContent.payload.content,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            content.text = Html.fromHtml(newsContent.payload.content)
        }
    }

    private fun restore(savedInstanceState: Bundle) {
        val savedNews = savedInstanceState.getCharSequence(CONTENT_KEY)
        savedNews?.let {
            content.text = savedNews
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(CONTENT_KEY, content.text)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}
