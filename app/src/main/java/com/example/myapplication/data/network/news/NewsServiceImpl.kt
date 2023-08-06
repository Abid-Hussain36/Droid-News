package com.example.myapplication.data.network.news

import android.util.Log
import com.example.myapplication.data.network.news.dto.News
import com.example.myapplication.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.url

class NewsServiceImpl(private val client: HttpClient): NewsService {
    override suspend fun getNews(): News {
        return try {
            client.get {
                url(Constants.TEST_URL)
            }
        } catch(e: RedirectResponseException){
            //300 HTTP Code
            Log.d("300", e.response.status.description)
            News()
        } catch(e: ClientRequestException){
            //400 HTTP Code
            Log.d("400", e.response.status.description)
            News()
        } catch(e: ServerResponseException){
            //500 HTTP Code
            Log.d("500", e.response.status.description)
            News()
        }
    }
}