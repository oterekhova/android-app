package com.example.myapplication.data

import com.example.myapplication.data.api.NewsApi
import com.example.myapplication.data.api.RetrofitApiClient

object Dependencies {
    val newsApi: NewsApi by lazy { RetrofitApiClient.create() }
}