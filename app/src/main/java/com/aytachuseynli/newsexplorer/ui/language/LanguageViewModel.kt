package com.aytachuseynli.newsexplorer.ui.language

import androidx.lifecycle.ViewModel
import com.aytachuseynli.newsexplorer.common.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val sp:SharedPrefManager)  : ViewModel() {

    fun saveLang(lang:String){
        sp.saveLang(lang)

    }


}