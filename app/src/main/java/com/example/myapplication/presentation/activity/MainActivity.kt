package com.example.myapplication.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.presentation.main.news_content_screen.NewsContentFragment
import com.example.myapplication.presentation.main.news_main_screen.NewsMainFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        displayNewsList()
    }

    private fun displayNewsList() {
        val newsMainFragment = NewsMainFragment.newInstance()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.news_frame, newsMainFragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

    fun displaySelectedNews(id: String) {
        val newsContentFragment = NewsContentFragment.newInstance(id)
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.news_frame, newsContentFragment)
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }

}
