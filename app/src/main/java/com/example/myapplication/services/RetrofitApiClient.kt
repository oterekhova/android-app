package com.example.myapplication.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiClient {

    companion object Factory {
        fun create(): NewsApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.tinkoff.ru/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }

}