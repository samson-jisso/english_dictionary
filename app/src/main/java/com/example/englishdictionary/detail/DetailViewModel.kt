package com.example.englishdictionary.detail

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.englishdictionary.db.SavedWords
import com.example.englishdictionary.db.SavedWordsDao
import com.example.englishdictionary.network.RetroInterface
import com.example.englishdictionary.network.RetrofitInstance
import com.example.englishdictionary.network.dataClasses.DataResult
import com.example.englishdictionary.network.dataClasses.UserError
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel(
    private val database: SavedWordsDao,
    application: Application
) : AndroidViewModel(application) {
    var searchedString: MutableLiveData<List<DataResult>?> = MutableLiveData()
    var userErrorTitle: String = ""

    fun getResult(): MutableLiveData<List<DataResult>?> {
        return searchedString
    }

    fun onSaveTracking(
        wordId: Int,
        word: String,
        partOfSpeech: String,
        meanings: String,
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val definitions = SavedWords(wordId, word, partOfSpeech, meanings)
            val oldWord = database.getWord(word)
            if (oldWord?.word === null) {
                insert(definitions)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "word successfully saved!!!", Toast.LENGTH_SHORT).show()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "The word already exists!!!", Toast.LENGTH_SHORT).show()
                }
            }
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
                    val res = response.errorBody()
                    val gson = Gson()
                    val error = gson.fromJson(res?.charStream(), UserError::class.java)
                    val errorTitle = error.title

                    if (response.code() == 404) {
                        userErrorTitle = errorTitle
                    }
                    searchedString.postValue(null)
                }
            }
        })
    }
}