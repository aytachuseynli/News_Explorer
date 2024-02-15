package com.aytachuseynli.newsexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedDTO::class], version = 1,exportSchema = true)
abstract class SavedDB: RoomDatabase() {

    abstract fun getSavedDao():SavedDAO

}