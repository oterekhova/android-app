package com.example.myapplication.services

import com.example.myapplication.data.News
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface NewsApiService {

    @GET("news")
    fun getNews(): Call<News>

}