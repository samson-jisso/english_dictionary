package com.example.englishdictionary.home

import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.englishdictionary.db.HistoryWords
import com.example.englishdictionary.db.HistoryWordsDao
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
import java.io.IOException

enum class WordApiStatus { LOADING, ERROR, DONE }
class DetailViewModel(
    private val databaseSavedWord: SavedWordsDao,
    private val databaseHistoryWords:HistoryWordsDao,
    application: Application
) : AndroidViewModel(application) {
    var inputText: MutableLiveData<String> = MutableLiveData()
    var searchedString: MutableLiveData<List<DataResult>?> = MutableLiveData()
    var userErrorTitle: String = ""
    var word:String = ""
    private var mediaPlayer: MediaPlayer? = null
    fun getResult(): MutableLiveData<List<DataResult>?> {
        return searchedString
    }

    fun onSaveTracking(
        wordId: Int,
        word: String,
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val definitions = SavedWords(wordId, word)
            val oldWord = databaseSavedWord.getWord(word)
            if (oldWord?.word === null) {
                insertSavedWord(definitions)
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
    fun mediaPlayer(url:String?) {
        viewModelScope.launch (Dispatchers.Default){
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                mediaPlayer!!.setDataSource(url)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    fun onSearchWord(wordId: Int, userWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val oldWord = databaseHistoryWords.getWord(userWord)
            val newWord = HistoryWords(wordId, userWord)
            if (oldWord?.word === null) {
                insertHistoryWord(newWord)
            }
        }
    }
    private suspend fun insertHistoryWord(word: HistoryWords) {
        databaseHistoryWords.insert(word)
    }
    private suspend fun insertSavedWord(newWord: SavedWords) {
        databaseSavedWord.insert(newWord)
    }

    fun getDefinition(word: String?) {
        val retroService = RetrofitInstance.getRetroInstance().create(RetroInterface::class.java)
        val call = retroService.searchString(word)
        call.enqueue(object : Callback<List<DataResult>> {
            override fun onFailure(call: Call<List<DataResult>>, t: Throwable) {
                userErrorTitle = "server error retry"
                searchedString.postValue(null)
            }

            override fun onResponse(
                call: Call<List<DataResult>>,
                response: Response<List<DataResult>>
            ) {
                if (response.isSuccessful) {
                    searchedString.postValue(response.body())
                } else {
                    if (response.code() == 404) {
                        val res = response.errorBody()
                        val gson = Gson()
                        val error = gson.fromJson(res?.charStream(), UserError::class.java)
                        val errorTitle = error.title
                        userErrorTitle = errorTitle
                    }else {
                        userErrorTitle = "server error retry"
                    }
                    searchedString.postValue(null)
                }
            }
        })
    }
}