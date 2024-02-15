package com.aytachuseynli.newsexplorer.data.network.dto

data class NewsDTO(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)