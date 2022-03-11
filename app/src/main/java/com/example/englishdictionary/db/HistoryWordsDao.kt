package com.example.englishdictionary.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import  androidx.room.Delete


@Dao
interface HistoryWordsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(wordDef: HistoryWords)
    @Query("select * from history_words_table order by wordId asc")
    fun getAllWord(): LiveData<List<HistoryWords>>
    @Query("select * from history_words_table where word = :word")
    suspend fun getWord(word:String):HistoryWords?
    @Delete
    suspend fun deleteWord(word:HistoryWords)

    @Query("delete from history_words_table")
    suspend fun deleteAllWord()
}