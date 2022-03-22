package com.example.englishdictionary.db

import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_words_table")
data class SavedWords(
    @PrimaryKey(autoGenerate = true)
    var wordId: Int,
    @ColumnInfo(name = "word")
    var word: String
)
