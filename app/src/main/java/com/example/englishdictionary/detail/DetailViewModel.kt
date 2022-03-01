package com.example.englishdictionary.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.englishdictionary.db.SavedWords
import com.example.englishdictionary.db.SavedWordsDao
import com.example.englishdictionary.network.RetroInterface
import com.example.englishdictionary.network.RetrofitInstance
import com.example.englishdictionary.network.dataClasses.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val database: SavedWordsDao,
    application: Application
) : AndroidViewModel(application) {
    var searchedString: MutableLiveData<List<DataResult>?> = MutableLiveData()

    fun getResult(): MutableLiveData<List<DataResult>?> {
        return searchedString
    }

    fun onSaveTracking(wordId: Int, word: String, partOfSpeech: String, meanings: String) {
        viewModelScope.launch(Dispatchers.IO){
            val definitions = SavedWords(wordId, word, partOfSpeech, meanings)
                insert(definitions)
        }
    }
    private suspend fun insert(newWord: SavedWords) {
        database.insert(newWord)
    }

    fun getDefinition(word: String?) {
        val retroService = RetrofitInstance.getRetroInstance().create(RetroInterface::class.java)
        val call = retroService.searchString(word)
        call.enqueue(object : Callback<List<DataResult>> {
            override fun onFailure(call: Call<List<DataResult>>, t: Throwable) {
                searchedString.postValue(null)
            }

            override fun onResponse(
                call: Call<List<DataResult>>,
                response: Response<List<DataResult>>
            ) {
                if (response.isSuccessful) {
                    searchedString.postValue(response.body())
                } else {
                    searchedString.postValue(null)
                }
            }
        })
    }
}