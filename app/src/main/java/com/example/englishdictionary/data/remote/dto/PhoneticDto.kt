package com.example.englishdictionary.data.remote.dto

import com.example.englishdictionary.domain.model.Phonetic

data class PhoneticDto(
    val audio: String,
    val license: LicenseXDto,
    val sourceUrl: String,
    val text: String
) {
    fun toPhonetic():Phonetic {
        return Phonetic(
            audio = audio,
            text = text
        )
    }
}