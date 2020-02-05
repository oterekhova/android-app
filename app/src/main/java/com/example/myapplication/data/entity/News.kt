package com.example.myapplication.data.entity

data class News(val resultCode: String,
                val payload: ArrayList<NewsContent>,
                val trackingId: String)