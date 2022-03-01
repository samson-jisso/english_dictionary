package com.example.englishdictionary.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishdictionary.db.SavedWordsDao

class DetailViewModelFactory( private val dataSource: SavedWordsDao,private val application: Application):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dataSource,application ) as T
        }
        throw IllegalArgumentException("unknown ViewModelClass")
    }
}