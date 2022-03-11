package com.example.englishdictionary.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishdictionary.db.HistoryWords
import com.example.englishdictionary.db.SavedWords
import com.example.englishdictionary.db.SavedWordsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val database:SavedWordsDao,
    application: Application
) : AndroidViewModel(application) {
 val words = database.getAllWord()
    fun deleteWord(word: SavedWords){
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteWord(word)
        }
    }
    fun deleteAllWord() {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteAllWord()
        }
    }
}