package com.example.myapplication.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilImage(imageUrl: String){
    val painter = rememberImagePainter(data = imageUrl)
    val painterState = painter.state
    if(painterState is ImagePainter.State.Loading){
            CircularProgressIndicator(
                modifier = Modifier.padding(20.dp)
            )
    }
    else if(painterState is ImagePainter.State.Error){
        Unit
    }
    else {
        Image(
            painter = painter,
            contentDescription = "Article Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
    }
}