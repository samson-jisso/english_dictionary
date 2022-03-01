package com.example.englishdictionary.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SavedWordsDao {
    @Insert
    suspend fun insert(wordDef: SavedWords)
    @Query("Select * From saved_Words_table where word = :word")
    fun getWord(word: String): SavedWords?

    @Query("select * from saved_words_table order by wordId DESC")
    fun getAllWord(): LiveData<List<SavedWords>>

}