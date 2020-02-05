package com.example.myapplication.presentation.main.news_content_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R


class NewsContentFragment : Fragment() {

    companion object {
        fun newInstance(content: String): NewsContentFragment {
            val newsContentFragment =
                NewsContentFragment()
            val args = Bundle()
            args.putString("content", content)
            newsContentFragment.arguments = args
            return newsContentFragment
        }
    }

    private lateinit var viewModel: NewsContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            NewsContentViewModel()
        // TODO: Use the ViewModel
    }

}
