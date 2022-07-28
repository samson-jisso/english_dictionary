package com.example.englishdictionary.domain.model

data class WordInfo(
    val meanings: List<Meaning>,
    val phonetic: String?,
    val phonetics: List<Phonetic>,
    val word: String
)
