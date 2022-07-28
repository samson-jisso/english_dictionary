package com.example.englishdictionary
//
//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.example.englishdictionary.db.HistoryDao
//import com.example.englishdictionary.db.SavedWords
//import com.example.englishdictionary.db.SavedWordsDao
//import com.example.englishdictionary.db.WordDatabase
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.io.IOException
//
///**
// * This is not meant to be a full set of tests. For simplicity, most of your samples do not
// * include tests. However, when building the Room, it is helpful to make sure it works before
// * adding the UI.
// */
//
//@RunWith(AndroidJUnit4::class)
//class WordDatabaseTest {
//
//    private lateinit var savedWordsDao: SavedWordsDao
//    private lateinit var historyDao: HistoryDao
//    private lateinit var db: WordDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
//        db = Room.inMemoryDatabaseBuilder(context, WordDatabase::class.java)
//            // Allowing main thread queries, just for testing.
//            .allowMainThreadQueries()
//            .build()
//        savedWordsDao = db.savedWordsDao
//        historyDao = db.historyDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetWord() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//
//        val word = SavedWords(context)
//        savedWordsDao.insert(word)
//        val tonight = savedWordsDao.getWord()
//        assertEquals(tonight?.word, -1)
//    }
//}

