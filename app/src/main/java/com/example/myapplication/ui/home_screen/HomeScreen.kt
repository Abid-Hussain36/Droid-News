package com.example.myapplication.ui.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.data.network.news.NewsService
import com.example.myapplication.util.Constants
import io.ktor.http.URLBuilder

@Composable
fun HomeScreen(){
    val newsService = NewsService.create()
    val articles = produceState(
        initialValue = listOf(),
        producer = {
            value = newsService.getNews().articles.toList()
        }
    )

    LazyColumn{
        items(articles.value){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                ){
                    if(it.urlToImage != null){
                        CoilImage(imageUrl = it.urlToImage.toString())
                    }
                    Text(
                        text = it.title.toString()
                    )
                    Text(
                        text = it.source?.name.toString()
                    )
                    Text(
                        text = it.description.toString()
                    )
                    Text(
                        text = it.publishedAt.toString()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilImage(imageUrl: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ){
        val painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                transformations(
                    RoundedCornersTransformation(30f),
                )
                scale(Scale.FIT)
            }
        )
        val painterState = painter.state
        Image(painter = painter, contentDescription = "Article Image")
        if(painterState is ImagePainter.State.Loading){
            CircularProgressIndicator()
        }
    }
}