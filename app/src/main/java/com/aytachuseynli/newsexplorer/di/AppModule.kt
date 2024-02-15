package com.aytachuseynli.newsexplorer.di

import android.content.Context
import com.aytachuseynli.newsexplorer.common.utils.SharedPrefManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedManager(@ApplicationContext context: Context) = SharedPrefManager(context)

}