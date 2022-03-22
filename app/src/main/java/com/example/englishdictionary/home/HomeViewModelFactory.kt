package com.example.englishdictionary.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishdictionary.db.HistoryWordsDao
import com.example.englishdictionary.db.SavedWordsDao

class HomeViewModelFactory(private val dataSourceSavedWords: SavedWordsDao, private val dataSourceHistoryWords:HistoryWordsDao, private val application: Application):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dataSourceSavedWords,dataSourceHistoryWords,application ) as T
        }
        throw IllegalArgumentException("unknown ViewModelClass")
    }
}