package com.example.englishdictionary.detail

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.englishdictionary.util.SharedViewModel
import com.example.englishdictionary.databinding.DetailFragmentBinding
import com.example.englishdictionary.db.WordDatabase

class DetailFragment : Fragment() {
    private lateinit var binding: DetailFragmentBinding
    private lateinit var viewModel: DetailViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application: Application = requireNotNull(this.activity).application
        val savedWordSource = WordDatabase.getInstance(application).savedWordsDao
        val viewModelFactory = DetailViewModelFactory(savedWordSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        binding = DetailFragmentBinding.inflate(this.layoutInflater)
        binding.apply {
            progressBar.visibility = View.VISIBLE
            loadingText.visibility = View.VISIBLE
        }
        initViewModel()
        sharedViewModel.wordSearched.observe(viewLifecycleOwner) {
            viewModel.getDefinition(it)
        }
        binding.apply {
            save.setOnClickListener {
                    viewModel.onSaveTracking(
                        0,
                        wordSearched.text.toString(),
                        partOfSpeechDetail.text.toString(),
                        meaningDetail.text.toString(),
                        it.context
                    )
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.getResult().observe(viewLifecycleOwner) {
            if (it == null) {
                binding.apply {
                    progressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                    userError.visibility = View.VISIBLE
                    userError.text = viewModel.userErrorTitle
                }
            } else {
                binding.apply {
                    progressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
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