package com.example.myapplication.data

data class NewsContent(
    val id: String,
    val name: String,
    val text: String,
    val publicationDate: PublicationDate,
    val bankInfoTypeId: Int
)