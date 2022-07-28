package com.example.englishdictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.englishdictionary.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordDef(wordInfoEntity: List<WordInfoEntity>)

    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordDef(word:String):List<WordInfoEntity>

    @Query("DELETE FROM wordinfoentity WHERE word IN(:words)")
    suspend fun deleteWordDef(words: List<String>)
    //were if i change the list string to list wordInfoEntity to delete
}