package com.example.englishdictionary.domain.model


data class Meaning(
    val antonyms: List<String>?,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>?,
    val example:String
)
