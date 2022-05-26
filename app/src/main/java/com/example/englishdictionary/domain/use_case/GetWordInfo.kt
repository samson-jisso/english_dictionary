package com.example.englishdictionary.domain.use_case

import com.example.englishdictionary.core.util.Resource
import com.example.englishdictionary.domain.model.WordInfo
import com.example.englishdictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(
   private val repository: WordInfoRepository
) {
    operator fun invoke(word:String):Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow {  }
        }
        return repository.getWordInfo(word)
    }
}