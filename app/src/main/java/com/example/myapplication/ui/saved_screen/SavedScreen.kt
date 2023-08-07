package com.example.myapplication.ui.saved_screen

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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.R
import com.example.myapplication.data.database.SavedArticle
import com.example.myapplication.data.database.SavedArticleDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SavedScreen(database: SavedArticleDB){
    val savedArticleDatabaseList by database.savedArticleDAO().getAllSavedArticles().observeAsState(initial = emptyList())
    var searchText by remember{
        mutableStateOf("")
    }
    var savedArticleList = if(searchText.isEmpty()){
        savedArticleDatabaseList
    } else{
        savedArticleDatabaseList.filter { it.title.toString().toLowerCase().contains(searchText) }
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(20.dp)
    ) {
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
                    .fillMaxWidth()
                    .fillMaxHeight(),
                textStyle = TextStyle(
                    fontSize = 24.sp
                )
            )
        }

        displaySavedArticles(
            savedArticleList = savedArticleList,
            scope = scope,
            database = database
        )
    }
}

@Composable
fun displaySavedArticles(savedArticleList: List<SavedArticle>, scope: CoroutineScope, database: SavedArticleDB){
    LazyColumn(
        modifier = Modifier.padding(top = 28.dp)
    ){
        items(savedArticleList){
            Card(
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text(text = it.title.toString())
                Button(onClick = {
                    scope.launch {
                        database.savedArticleDAO().deleteSavedArticle(it)
                    }
                }) {
                    Text("Delete")
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