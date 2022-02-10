package com.example.englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.englishdictionary.databinding.*
import com.example.englishdictionary.favorite.BookmarkFragment
import com.example.englishdictionary.history.HistoryFragment
import com.example.englishdictionary.home.HomeFragment
import com.example.englishdictionary.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val bookmarkFragment = BookmarkFragment()
    private val historyFragment = HistoryFragment()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavView = binding.bottomNav

        bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    setThatFragment(searchFragment)
                }
                R.id.bookmark -> {
                    setThatFragment(bookmarkFragment)
                }
                R.id.history -> {
                    setThatFragment(historyFragment)
                }
                else -> {
                    setThatFragment(homeFragment)
                }
            }
            true
        }
    }

    private fun setThatFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment, fragment)
            commit()
        }
    }
}