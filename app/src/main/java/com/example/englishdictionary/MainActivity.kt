package com.example.englishdictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.englishdictionary.databinding.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    }
//        val bottomNavView = binding.bottomNav
//        bottomNavView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.detail -> {
//                    setThatFragment(detailFragment)
//                }
//                R.id.bookmark -> {
//                    setThatFragment(bookmarkFragment)
//                }
//                R.id.history -> {
//                    setThatFragment(historyFragment)
//                }
//                else -> {
//                    setThatFragment(homeFragment)
//                }
//            }
//            true
//        }
//    }
//
//    private fun setThatFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.nav_host_fragment, fragment)
//            commit()
//        }
//    }