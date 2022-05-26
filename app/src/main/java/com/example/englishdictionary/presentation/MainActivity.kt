package com.example.englishdictionary.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.englishdictionary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var _binding:ActivityMainBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(this.layoutInflater)

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}