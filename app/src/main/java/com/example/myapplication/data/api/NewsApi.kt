package com.example.myapplication.data.api

import com.example.myapplication.data.entity.News
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {

    @GET("news")
    fun getNews(): Single<News>

}