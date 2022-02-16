package com.example.englishdictionary.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.englishdictionary.R
import com.example.englishdictionary.databinding.DetailFragmentBinding
import com.example.englishdictionary.home.HomeViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: DetailViewModel
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        binding = DetailFragmentBinding.inflate(layoutInflater)
        homeViewModel.wordSearched.observe(viewLifecycleOwner) {
            viewModel.getDefinition(it)
        }
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getResult().observe(viewLifecycleOwner) {
            if (it == null) {
                println(null)
            } else {
                binding.apply {
                    wordSearched.text = it[0].word
                    soundDetail.text = it[0].phonetic
                    partOfSpeechDetail.text = it[0].meanings[0].partOfSpeech
                    meaningDetail.text = it[0].meanings[0].definitions[0].definition
                }
            }

        }
    }


}