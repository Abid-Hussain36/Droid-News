package com.example.myapplication.ui.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.R
import com.example.myapplication.data.database.SavedArticle
import com.example.myapplication.data.database.SavedArticleDB
import com.example.myapplication.data.network.news.NewsService
import com.example.myapplication.data.network.news.dto.Article
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDate

@Composable
fun HomeScreen(navController: NavController, database: SavedArticleDB){
    val newsService = NewsService.create()
    val savedArticleList by database.savedArticleDAO().getAllSavedArticles().observeAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var searchText by remember{
        mutableStateOf("")
    }
    var searchURL by remember {
        mutableStateOf("https://newsapi.org/v2/top-headlines?country=us&apiKey=0701b3e1ea1c4e79b1511178e78383f3")
    }
    val dateScope = LocalDate.now().minusDays(7)
    val scopeYear = dateScope.year.toString()
    val scopeMonth = if(dateScope.month.value.toString().length == 1){
        "0${dateScope.month.value.toString()}"
    } else{
        dateScope.month.value.toString()
    }
    val scopeDay = if(dateScope.dayOfMonth.toString().length == 1){
        "0${dateScope.dayOfMonth.toString()}"
    } else{
        dateScope.dayOfMonth.toString()
    }
    val articles = produceState<List<Article>>(
        initialValue = listOf(), searchURL,
        producer = {
            value = newsService.getNews(searchURL).articles.toList()
        }
    )

    Column(
        modifier = Modifier.padding(20.dp)
    ){
        Row(
            modifier = Modifier
                //.padding(20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight(.1f)
        ){
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth(.75f)
                    .fillMaxHeight(),
                textStyle = TextStyle(
                    fontSize = 24.sp
                )
            )
            Spacer(modifier = Modifier.padding(start = 0.dp, end = 15.dp))
            Button(
                modifier = Modifier
                    .fillMaxHeight(),
                onClick = {
                    searchURL = "https://newsapi.org/v2/everything?q=${searchText}&from=${scopeYear}-${scopeMonth}-${scopeDay}&language=en&sortBy=relevancy&apiKey=0701b3e1ea1c4e79b1511178e78383f3"
                },
                shape = RoundedCornerShape(30.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search Icon",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        LazyColumn(
            modifier = Modifier.padding(top = 28.dp)
        ) {
            items(articles.value) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        if (it.urlToImage != null) {
                            CoilImage(imageUrl = it.urlToImage.toString())
                        }
                        Text(
                            text = it.title.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = it.source?.name.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = it.description.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${it.publishedAt.toString().substring(5, 7)}/${it.publishedAt.toString().substring(8, 10)}/${it.publishedAt.toString().substring(0, 4)}" ,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 12.dp)
                        ) {
                            Button(
                                onClick = {
                                          //Needed to encode the article URL for it to be registered as a String in Navigation.
                                          val encodedUrl = URLEncoder.encode(it.url.toString(), StandardCharsets.UTF_8.toString())
                                          navController.navigate(com.example.myapplication.util.navigation.Article.route + "/$encodedUrl")
                                },
                                modifier = Modifier.fillMaxWidth(.47f)
                            ) {
                                Text(text = "Open")
                            }
                            Spacer(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                            Button(
                                onClick = {
                                    //Room operations must usually be performed in Coroutines.
                                    scope.launch{
                                        database.savedArticleDAO()
                                            .insertSavedArticle(it.toSavedArticle())
                                    }
                                          },
                                modifier = Modifier.fillMaxWidth(1f)
                            ) {
                                Text(text = "Save")
                            }
                        }
                        Text(text = savedArticleList.size.toString())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilImage(imageUrl: String){
        val painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                transformations(
                    RoundedCornersTransformation(30f),
                )
            }
        )
        val painterState = painter.state
        if(painterState is ImagePainter.State.Loading){
            CircularProgressIndicator()
        }
        else if(painterState is ImagePainter.State.Error){
            Image(
                painter = painterResource(R.drawable.baseline_newspaper_24),
                contentDescription = "Article Image",
                modifier = Modifier
                    .width(500.dp)
                    .height(500.dp),
                contentScale = ContentScale.Crop
            )
        }
        else{
            Image(
                painter = painter,
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }