package com.example.myapplication.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedArticle(
    @PrimaryKey(autoGenerate = true) val articleID: Int = 0,
    val sourceName: String? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)