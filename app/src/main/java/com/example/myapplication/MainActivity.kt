package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.myapplication.data.database.SavedArticleDB
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.util.navigation.Navigation

class MainActivity : ComponentActivity() {
    val database by lazy{
        Room.databaseBuilder(
            applicationContext,
            SavedArticleDB::class.java,
            "savedArticleDB.db"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(database)
                }
            }
        }
    }
}