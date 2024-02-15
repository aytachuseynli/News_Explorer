package com.aytachuseynli.newsexplorer.ui.saved

import com.aytachuseynli.newsexplorer.domain.model.NewsUiModel

sealed class SavedUiState {

    object Loading : SavedUiState()
    data class SuccessSavedData(val list : List<NewsUiModel>) : SavedUiState()
    data class Error(val message: String) : SavedUiState()
}