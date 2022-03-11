package com.example.englishdictionary.home

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.englishdictionary.R
import com.example.englishdictionary.util.SharedViewModel
import com.example.englishdictionary.databinding.HomeFragmentBinding
import com.example.englishdictionary.db.WordDatabase

class HomeFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var  viewModel : HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        val application:Application = requireNotNull(this.activity).application
        val historyWordSource = WordDatabase.getInstance(application).historyWordsDao
        val viewModelFactory = HomeViewModelFactory(historyWordSource,application)
        viewModel = ViewModelProvider(this,viewModelFactory)[HomeViewModel::class.java]
        binding.searchBtn.setOnClickListener {view:View ->
            sharedViewModel.searchWord(binding.inputText.text.toString())
            viewModel.onSearchWord(0,binding.inputText.text.toString())
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        return binding.root
    }


}