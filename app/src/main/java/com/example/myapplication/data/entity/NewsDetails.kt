package com.example.myapplication.data.entity

data class NewsDetails(
    val id: String,
    val name: String,
    val text: String,
    val publicationDate: PublicationDate,
    val bankInfoTypeId: Int
)