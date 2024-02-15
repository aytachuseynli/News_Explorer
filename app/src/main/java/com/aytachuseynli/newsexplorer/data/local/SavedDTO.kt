package com.aytachuseynli.newsexplorer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("saved_news")
data class SavedDTO (
    val author: String? ,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val url: String?,
    val urlToImage: String?
)