package com.aytachuseynli.newsexplorer.di

import android.content.Context
import androidx.room.Room
import com.aytachuseynli.newsexplorer.common.utils.Constants.BASE_URL
import com.aytachuseynli.newsexplorer.data.local.SavedDAO
import com.aytachuseynli.newsexplorer.data.local.SavedDB
import com.aytachuseynli.newsexplorer.data.network.api.NewsApiService
import com.aytachuseynli.newsexplorer.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit() = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()).build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(NewsApiService::class.java)

    @Singleton
    @Provides
    fun provideAuthRepository(apiService: NewsApiService, savedDao: SavedDAO): NewsRepository  = NewsRepository(apiService,savedDao)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): SavedDB =
        Room.databaseBuilder(
            context,
            SavedDB::class.java,
            "SavedNewsDB"
        ).build()

    @Singleton
    @Provides
    fun provideFavDao(db: SavedDB): SavedDAO = db.getSavedDao()

}