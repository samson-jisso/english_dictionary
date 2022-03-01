package com.example.englishdictionary.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedWords::class], version = 1, exportSchema = false)
abstract class WordDatabase : RoomDatabase() {
    abstract val savedWordsDao: SavedWordsDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null
        fun getInstance(context: Context): WordDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}