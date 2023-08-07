package com.example.myapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedArticleDAO {
    @Query("SELECT * from SavedArticle")
    fun getAllSavedArticles(): LiveData<List<SavedArticle>>

    @Insert
    suspend fun insertSavedArticle(savedArticle: SavedArticle)

    @Delete
    suspend fun deleteSavedArticle(savedArticle: SavedArticle)
}