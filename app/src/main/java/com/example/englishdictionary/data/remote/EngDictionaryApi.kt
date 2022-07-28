package com.example.englishdictionary.data.remote

import com.example.englishdictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface EngDictionaryApi {
    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordDef(@Path("word") word:String):List<WordInfoDto>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }
}