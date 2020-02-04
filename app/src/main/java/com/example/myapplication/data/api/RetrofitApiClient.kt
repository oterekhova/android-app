package com.example.myapplication.data.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiClient {

    companion object Factory {
        fun create(): NewsApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.tinkoff.ru/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NewsApi::class.java)
        }
    }

}