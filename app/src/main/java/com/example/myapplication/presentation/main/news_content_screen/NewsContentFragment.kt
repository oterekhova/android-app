package com.example.myapplication.presentation.main.news_content_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data.api.RetrofitApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_content.*
import kotlinx.android.synthetic.main.news_content.view.*


class NewsContentFragment : Fragment() {

    private val CONTENT_KEY = "serializable_content_key"
    private var disposable: CompositeDisposable? = null

    companion object {

        private val NEWS_ID = "news_id"

        fun newInstance(content: String): NewsContentFragment {
            val newsContentFragment = NewsContentFragment()
            val args = Bundle()
            args.putString(NEWS_ID, content)
            newsContentFragment.arguments = args
            return newsContentFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_content, container, false)
        disposable = CompositeDisposable()

        if (savedInstanceState != null) {
            restore(savedInstanceState)
        } else {
            displayNewsContent(view.content)
        }

        return view
    }

    private fun displayNewsContent(textView: TextView) {
        val newsId = arguments!!.getString(NEWS_ID)
        setNewsContent(newsId!!, textView)
    }

    private fun setNewsContent(id: String, textView: TextView) {
        disposable?.add(
            RetrofitApiClient.create().getNewsContent(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { result -> textView.text = result.payload.content },
                    { error -> error.printStackTrace() }
                )
        )
    }

    private fun restore(savedInstanceState: Bundle) {
        if (savedInstanceState.getSerializable(CONTENT_KEY) != null) {
            val savedNews = savedInstanceState.getCharSequence(CONTENT_KEY)
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
