package com.example.myapplication.data.api

import com.example.myapplication.data.entity.News
import com.example.myapplication.data.entity.NewsContent
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("news")
    fun getNews(): Single<News>

    @GET("news_content")
    fun getNewsContent(@Query("id") id: String) : Single<NewsContent>

}