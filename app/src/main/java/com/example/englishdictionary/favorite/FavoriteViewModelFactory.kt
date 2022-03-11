package com.example.englishdictionary.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishdictionary.db.SavedWordsDao

class FavoriteViewModelFactory(private val dataSource: SavedWordsDao, private val application: Application):ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(dataSource,application ) as T
        }
        throw IllegalArgumentException("unknown ViewModelClass")
    }
}