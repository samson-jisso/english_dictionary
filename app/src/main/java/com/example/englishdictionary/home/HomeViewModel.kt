package com.example.englishdictionary.home

import android.app.Application
import androidx.lifecycle.*
import com.example.englishdictionary.db.HistoryWords
import com.example.englishdictionary.db.HistoryWordsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val database: HistoryWordsDao,
    application: Application
) : AndroidViewModel(application) {
    fun onSearchWord(wordId: Int, userWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val oldWord = database.getWord(userWord)
            val newWord = HistoryWords(wordId, userWord)
            if (oldWord?.word === null) {
                insert(newWord)
            }
        }
    }

    private suspend fun insert(word: HistoryWords) {
        database.insert(word)
    }
}