package com.example.myapplication.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedArticle::class], version = 1)
abstract class SavedArticleDB: RoomDatabase() {
    abstract fun savedArticleDAO(): SavedArticleDAO
}