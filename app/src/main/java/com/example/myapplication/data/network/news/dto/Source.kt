package com.example.myapplication.data.network.news.dto

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String? = null,
    val name: String? = null
)