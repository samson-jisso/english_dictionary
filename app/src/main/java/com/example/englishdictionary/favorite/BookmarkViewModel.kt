package com.example.englishdictionary.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.englishdictionary.db.SavedWordsDao

class BookmarkViewModel (
    private val database:SavedWordsDao,
    application: Application
) : AndroidViewModel(application) {
 val words = database.getAllWord()

}