package com.aytachuseynli.newsexplorer.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aytachuseynli.newsexplorer.common.utils.Resource
import com.aytachuseynli.newsexplorer.data.local.SavedDTO
import com.aytachuseynli.newsexplorer.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: NewsRepository) : ViewModel() {

    private val _detailState = MutableLiveData<DetailUiState?>()
    val detailState: LiveData<DetailUiState?> get() = _detailState


    fun addSaves(savedDTO: SavedDTO) {
        repo.addSaves(savedDTO)
        viewModelScope.launch {
            repo.addSaves(savedDTO).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailUiState.Loading
                    }

                    is Resource.Success -> {
                        _detailState.value = DetailUiState.SuccessSaveNews("News Saved")
                    }

                    is Resource.Error -> {
                        _detailState.value = DetailUiState.Error(it.exception)
                    }
                }
            }
        }


    }

    fun deleteSaves(savedDTO: SavedDTO) {
        viewModelScope.launch {
            repo.deleteNews(savedDTO).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _detailState.value = DetailUiState.Loading
                    }

                    is Resource.Success -> {
                        _detailState.value = DetailUiState.SuccessDeleteSave("News Deleted")
                    }

                    is Resource.Error -> {
                        _detailState.value = DetailUiState.Error(it.exception)
                    }
                }
            }
        }

    }

    fun isNewsSaved(title: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            repo.isNewsSaved(title).collectLatest {

                callback(it)

            }
        }

    }
    fun resetData(){
        _detailState.value=null
    }

}