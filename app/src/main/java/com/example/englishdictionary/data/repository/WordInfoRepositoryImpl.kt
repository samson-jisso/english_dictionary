package com.example.englishdictionary.data.repository

import com.example.englishdictionary.core.util.Resource
import com.example.englishdictionary.data.local.WordInfoDao
import com.example.englishdictionary.data.remote.EngDictionaryApi
import com.example.englishdictionary.domain.model.WordInfo
import com.example.englishdictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: EngDictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordDef = dao.getWordDef(word).map { it.toWordInfo() }
        emit(Resource.Loading(wordDef))

        try {
            val remoteWordDef = api.getWordDef(word)
            dao.deleteWordDef(remoteWordDef.map { it.word })
            dao.insertWordDef(remoteWordDef.map { it.toWordInfoEntity() })
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "oops, something happened.",
                    data = wordDef
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "couldn't reach server, check your internet setting!"
                )
            )
        }
    }
}