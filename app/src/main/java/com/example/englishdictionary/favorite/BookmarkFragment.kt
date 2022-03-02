package com.example.englishdictionary.favorite

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishdictionary.databinding.FavoriteFragmentBinding
import com.example.englishdictionary.db.WordDatabase

class BookmarkFragment : Fragment() {

    private lateinit var viewModel: BookmarkViewModel
    private lateinit var binding: FavoriteFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(layoutInflater)
        val application: Application = requireNotNull(this.activity).application
        val savedWordSource = WordDatabase.getInstance(application).savedWordsDao
        val viewModelFactory = BookmarkViewModelFactory(savedWordSource,application)
        viewModel = ViewModelProvider(this,viewModelFactory)[BookmarkViewModel::class.java]

        //binding.wordList.layoutManager = LinearLayoutManager(requireContext())
        val adapter = WordAdapter()
        binding.wordList.adapter = adapter

        viewModel.words.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it

            }
        }

        return binding.root
    }

}