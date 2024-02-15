package com.aytachuseynli.newsexplorer.common.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.aytachuseynli.newsexplorer.common.utils.Constants.APP_LANG
import com.aytachuseynli.newsexplorer.common.utils.Constants.SP_NAME

class SharedPrefManager (private val context: Context) {

    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    }

    private val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            SP_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    fun saveLang(lang: String?) {
        with(sharedPreferences.edit()) {
            putString(APP_LANG, lang)
            apply()
        }
    }
    fun getLang() = sharedPreferences.getString(APP_LANG, "en")



}