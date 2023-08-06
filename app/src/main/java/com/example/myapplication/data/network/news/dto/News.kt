package com.example.myapplication.data.network.news.dto

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: ArrayList<Article> = arrayListOf()
)
