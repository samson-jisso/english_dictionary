package com.example.englishdictionary.network

import com.example.englishdictionary.network.dataClasses.DataResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroInterface {
    @GET("en/{word}")
    fun searchString(@Path("word") word:String?) : Call<List<DataResult>>
}