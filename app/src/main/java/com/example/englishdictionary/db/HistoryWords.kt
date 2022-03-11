package com.example.englishdictionary.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_words_table")
data class HistoryWords(
    @PrimaryKey(autoGenerate = true)
    var wordId: Int,
    @ColumnInfo(name = "word")
    var word: String
)
