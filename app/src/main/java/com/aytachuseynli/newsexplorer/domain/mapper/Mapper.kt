package com.aytachuseynli.newsexplorer.domain.mapper

import com.aytachuseynli.newsexplorer.data.local.SavedDTO
import com.aytachuseynli.newsexplorer.data.network.dto.Article
import com.aytachuseynli.newsexplorer.domain.model.NewsUiModel

object Mapper {

    fun Article.toNewsUiModel() = NewsUiModel(
        author,
        content,
        description,
        publishedAt,
        title,
        url,
        urlToImage
    )

    fun List<Article>.toNewUiModelList()=map {
        NewsUiModel(
            it.author?:"Anonym",
            it.content?:"No Content",
            it.description?:"No description",
            it.publishedAt?:"No date",
            it.title?:"No Title",
            it.url?:"",
            it.urlToImage?:"Removed"
        )
    }

    fun NewsUiModel.toSavedDTO() = SavedDTO(
        author?:"unknown",
        content?:"unknown",
        description?:"unknown",
        publishedAt?:"unknown",
        title?:"unknown",
        url?:"unknown",
        urlToImage?:"unknown"
    )

    fun List<SavedDTO>.toNewUiModelL()=map {
        NewsUiModel(
            it.author?:"unknown",
            it.content?:"unknown",
            it.description?:"unknown",
            it.publishedAt?:"unknown",
            it.title?:"unknown",
            it.url?:"unknown",
            it.urlToImage?:"unknown"
        )
    }

    fun SavedDTO.toNewsUiModel() = NewsUiModel(
        author?:"unknown",
        content?:"unknown",
        description?:"unknown",
        publishedAt?:"unknown",
        title?:"unknown",
        url?:"unknown",
        urlToImage?:"unknown"
    )
}