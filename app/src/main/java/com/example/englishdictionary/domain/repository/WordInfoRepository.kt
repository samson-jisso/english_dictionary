package com.example.englishdictionary.domain.repository

import com.example.englishdictionary.core.util.Resource
import com.example.englishdictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word:String):Flow<Resource<List<WordInfo>>>
}