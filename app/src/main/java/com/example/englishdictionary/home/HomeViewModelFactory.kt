package com.example.englishdictionary.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishdictionary.db.HistoryWordsDao
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val dataSource:HistoryWordsDao,private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("unknown ViewModelClass")
    }
}