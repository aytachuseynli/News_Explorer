package com.aytachuseynli.newsexplorer.ui.detail

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class SuccessSaveNews(val message: String) : DetailUiState()
    data class SuccessDeleteSave(val message: String) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}