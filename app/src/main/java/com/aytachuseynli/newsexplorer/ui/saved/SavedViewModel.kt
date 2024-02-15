package com.aytachuseynli.newsexplorer.ui.saved

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aytachuseynli.newsexplorer.common.utils.Resource
import com.aytachuseynli.newsexplorer.domain.mapper.Mapper.toNewUiModelL
import com.aytachuseynli.newsexplorer.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repo: NewsRepository): ViewModel() {


    private val _savedState = MutableStateFlow<SavedUiState>(SavedUiState.Loading)
    val savedState get() = _savedState.asStateFlow()


    fun getSaves(){

        repo.getSaves().onEach {
            when(it){
                is Resource.Loading->{  _savedState.value=SavedUiState.Loading }
                is Resource.Success->{   it.data?.let { list-> _savedState.value=SavedUiState.SuccessSavedData(list.toNewUiModelL())
                    Log.e("saves", "$list" )}}
                is Resource.Error->{  _savedState.value=SavedUiState.Error(it.exception)  }
            }
        }.launchIn(viewModelScope)

    }


}