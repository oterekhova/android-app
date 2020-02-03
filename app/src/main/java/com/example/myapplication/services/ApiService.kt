package com.example.myapplication.services

import com.example.myapplication.data.News
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("news")
    fun getNews(): Observable<News>

}