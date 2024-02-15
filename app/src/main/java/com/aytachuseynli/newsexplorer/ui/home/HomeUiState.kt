package com.aytachuseynli.newsexplorer.ui.home

import com.aytachuseynli.newsexplorer.domain.model.NewsUiModel

sealed interface HomeUiState{
    object Loading :HomeUiState
    data class SuccessTopNewsData(val list : List<NewsUiModel>) : HomeUiState
    data class SuccessSearchData(val list : List<NewsUiModel>) : HomeUiState
    data class Error(val message : String) : HomeUiState
}