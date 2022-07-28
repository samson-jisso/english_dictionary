package com.example.englishdictionary.presentation

import com.example.englishdictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoState: List<WordInfo> = emptyList(),
    val isLoading:Boolean = false
)
