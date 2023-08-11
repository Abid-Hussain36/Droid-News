package com.example.myapplication.ui.saved_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.database.SavedArticle
import com.example.myapplication.data.database.SavedArticleDB
import com.example.myapplication.ui.composables.CoilImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SavedScreen(navController: NavController, database: SavedArticleDB){
    val savedArticleDatabaseList by database.savedArticleDAO().getAllSavedArticles().observeAsState(initial = emptyList())
    var searchText by remember{
        mutableStateOf("")
    }
    val savedArticleList = if(searchText.isEmpty()){
        savedArticleDatabaseList
    } else{
        savedArticleDatabaseList.filter { it.title.toString().toLowerCase().contains(searchText.toLowerCase()) }
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                //.padding(20.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .fillMaxHeight(.08f)
        ){
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                textStyle = TextStyle(
                    fontSize = 24.sp
                )
            )
        }
        if(savedArticleDatabaseList.isEmpty()){
            Column(
                modifier = Modifier
                    .padding(top = 28.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "You have no saved articles.",
                    fontSize = 20.sp
                )
            }
        }
        else{
            DisplaySavedArticles(
                savedArticleList = savedArticleList,
                scope = scope,
                navController = navController,
                database = database
            )
        }
    }
}

@Composable
fun DisplaySavedArticles(savedArticleList: List<SavedArticle>, scope: CoroutineScope, navController: NavController, database: SavedArticleDB){
    LazyColumn(
        modifier = Modifier.padding(top = 28.dp)
    ){
        items(savedArticleList){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if(it.urlToImage != null){
                        CoilImage(imageUrl = it.urlToImage)
                    }
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 10.dp
                        )
                    ){
                        if(it.title != null){
                            Text(
                                text = it.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if(it.sourceName != null){
                            Text(
                                text = it.sourceName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 2.dp),
                                color = Color.Gray
                            )
                        }
                        if(it.content != null){
                            Text(
                                text = it.content,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 8.dp),
                                color = Color.DarkGray
                            )
                        }
                        if(it.publishedAt != null){
                            Text(
                                text = "${
                                    it.publishedAt.substring(5, 7)
                                }/${
                                    it.publishedAt.substring(8, 10)
                                }/${it.publishedAt.substring(0, 4)}",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 6.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 16.dp)
                        ) {
                            Button(
                                onClick = {
                                    val encodedUrl = URLEncoder.encode(
                                        it.url.toString(),
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    navController.navigate(com.example.myapplication.util.navigation.Article.route + "/$encodedUrl")
                                },
                                modifier = Modifier.fillMaxWidth(.47f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF114bfa),
                                    contentColor = Color(0xFFFFFFFF)
                                )
                            ) {
                                Text(
                                    text = "Open",
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                            Button(
                                onClick = {
                                    scope.launch {
                                        database.savedArticleDAO().deleteSavedArticle(it)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF114bfa),
                                    contentColor = Color(0xFFFFFFFF)
                                )
                            ) {
                                Text(
                                    "Delete",
                                    fontSize = 16.sp,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}