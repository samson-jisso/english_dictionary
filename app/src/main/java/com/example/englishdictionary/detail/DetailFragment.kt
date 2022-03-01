package com.example.englishdictionary.detail

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.englishdictionary.databinding.DetailFragmentBinding
import com.example.englishdictionary.db.WordDatabase
import com.example.englishdictionary.home.HomeViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: DetailViewModel
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application: Application = requireNotNull(this.activity).application
        val savedWordSource = WordDatabase.getInstance(application).savedWordsDao
        val viewModelFactory = DetailViewModelFactory(savedWordSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        binding = DetailFragmentBinding.inflate(layoutInflater)
        val timer = object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.visibility = View.VISIBLE
                binding.loadingText.visibility = View.VISIBLE
            }

            override fun onFinish() {
                binding.progressBar.visibility = View.GONE
                binding.loadingText.visibility = View.GONE
            }
        }
        timer.start()
        initViewModel()
        homeViewModel.wordSearched.observe(viewLifecycleOwner) {
            viewModel.getDefinition(it)
        }
        binding.save.setOnClickListener {
            viewModel.onSaveTracking(
                0,
                binding.wordSearched.text.toString(),
                binding.partOfSpeechDetail.text.toString(),
                binding.meaningDetail.text.toString()
            )

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
                    wordSearched.visibility = View.VISIBLE
                    save.visibility = View.VISIBLE
                    share.visibility = View.VISIBLE
                    copy.visibility = View.VISIBLE
                    linearView.visibility = View.VISIBLE
                }
            }

        }
    }

}