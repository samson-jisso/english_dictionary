package com.example.englishdictionary.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {

    private var _wordSearched = MutableLiveData<String?>()
    val wordSearched : LiveData<String?> = _wordSearched
    fun searchWord(text:String) {
        _wordSearched.value = text
    }
}