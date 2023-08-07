package com.example.myapplication.data.network.news.dto

import com.example.myapplication.data.database.SavedArticle
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
){
    fun toSavedArticle(): SavedArticle{
        return SavedArticle(
            sourceName = source?.name,
            author = author,
            title = title,
            description = description,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt,
            content = content
        )
    }
}
