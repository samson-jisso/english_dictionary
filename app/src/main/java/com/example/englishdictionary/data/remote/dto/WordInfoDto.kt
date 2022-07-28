package com.example.englishdictionary.data.remote.dto

import com.example.englishdictionary.data.local.entity.WordInfoEntity
import com.example.englishdictionary.domain.model.WordInfo

data class WordInfoDto(
    val license: LicenseDto,
    val meanings: List<MeaningDto>,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val sourceUrls: List<String>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            phonetics = phonetics.map { it.toPhonetic() },
            word = word
        )
    }
}