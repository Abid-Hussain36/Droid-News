package com.example.myapplication.data.network.news

import com.example.myapplication.data.network.news.dto.News
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import okhttp3.HttpUrl

interface NewsService {
    suspend fun getNews(searchURL: String): News

    companion object{
        fun create(): NewsService{
            return NewsServiceImpl(
                client = HttpClient(Android){
                    install(JsonFeature){
                        serializer = KotlinxSerializer()
                    }
                    install(Logging){
                        level = LogLevel.ALL
                    }
                }
            )
        }
    }
}