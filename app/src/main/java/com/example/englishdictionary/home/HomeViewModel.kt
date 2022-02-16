package com.example.englishdictionary.home

import androidx.lifecycle.*
import com.example.englishdictionary.network.RetroInterface
import com.example.englishdictionary.network.RetrofitInstance
import com.example.englishdictionary.network.dataClasses.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.acl.Owner

class HomeViewModel : ViewModel() {
    private var _wordSearched = MutableLiveData<String?>()
    val wordSearched :LiveData<String?> = _wordSearched
    fun searchWord(text:String) {
        _wordSearched.value = text
    }
}