package com.example.englishdictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.englishdictionary.data.local.entity.WordInfoEntity

@Database(
    entities = [WordInfoEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class WordDatabase : RoomDatabase() {
    abstract val dao: WordInfoDao
}