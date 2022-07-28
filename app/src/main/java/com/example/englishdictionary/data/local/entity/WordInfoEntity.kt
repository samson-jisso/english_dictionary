package com.example.englishdictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.englishdictionary.domain.model.Meaning
import com.example.englishdictionary.domain.model.Phonetic
import com.example.englishdictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    val meanings: List<Meaning>,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>,
    val word: String,
    @PrimaryKey
    val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            phonetic = phonetic,
            phonetics = phonetics,
            word = word
        )
    }
}