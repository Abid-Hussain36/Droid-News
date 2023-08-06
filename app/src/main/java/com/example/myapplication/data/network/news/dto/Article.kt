package com.example.myapplication.data.network.news.dto

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    //Used null instead of Source()
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)
