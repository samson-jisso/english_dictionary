package com.example.englishdictionary.di

import android.app.Application
import androidx.room.Room
import com.example.englishdictionary.data.local.Converters
import com.example.englishdictionary.data.local.WordDatabase
import com.example.englishdictionary.data.remote.EngDictionaryApi
import com.example.englishdictionary.data.repository.WordInfoRepositoryImpl
import com.example.englishdictionary.data.util.GsonParser
import com.example.englishdictionary.domain.repository.WordInfoRepository
import com.example.englishdictionary.domain.use_case.GetWordInfo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }
    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db:WordDatabase,
        api: EngDictionaryApi
    ):WordInfoRepository {
        return WordInfoRepositoryImpl(api, db.dao)
    }
    @Provides
    @Singleton
    fun provideWordDatabase(app:Application):WordDatabase {
        return Room.databaseBuilder(
            app,
            WordDatabase::class.java,
            "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }
    @Provides
    @Singleton
    fun provideEngDictionaryApi():EngDictionaryApi {
        return Retrofit.Builder()
            .baseUrl(EngDictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EngDictionaryApi::class.java)
    }
}