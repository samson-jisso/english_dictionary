package com.example.englishdictionary.home

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishdictionary.databinding.HomeFragmentBinding
import com.example.englishdictionary.util.SharedViewModel
import com.example.englishdictionary.db.WordDatabase
import com.example.englishdictionary.network.dataClasses.Meanings
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), MeaningWordAdapter.Interaction {
    private var _binding: HomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var audioURl: String? = ""
    private lateinit var viewModel: DetailViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var meaningWordAdapter: MeaningWordAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application: Application = requireNotNull(this.activity).application
        val savedWordSource = WordDatabase.getInstance(application).savedWordsDao
        val historyWordSource = WordDatabase.getInstance(application).historyWordsDao
        val viewModelFactory = HomeViewModelFactory(savedWordSource,historyWordSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        _binding = HomeFragmentBinding.inflate(this.layoutInflater)
        binding.apply {
            textField.editText?.setOnClickListener {
                textField.error = null
                userError.visibility = View.GONE
                meaningList.visibility = View.GONE
                speaker.visibility = View.GONE
                progressBar.visibility = View.GONE
                loadingText.visibility = View.GONE
                lineText.visibility = View.GONE
                wordSearched.visibility = View.GONE
                sound.visibility = View.GONE
                save.visibility = View.GONE
                share.visibility = View.GONE
            }
            textField.setEndIconOnClickListener {
                meaningList.visibility = View.GONE
                userError.visibility = View.GONE
                speaker.visibility = View.GONE
                lineText.visibility = View.GONE
                wordSearched.visibility = View.GONE
                sound.visibility = View.GONE
                save.visibility = View.GONE
                share.visibility = View.GONE
                it.hideKeyboard()
                checkInput(textField.editText?.text.toString())
            }
        }
        initRecyclerView()
        viewModel.getResult().observe(viewLifecycleOwner) {
            if (it == null) {
                binding.apply {
                    loadingText.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    userError.visibility = View.VISIBLE
                    userError.text = viewModel.userErrorTitle
                }
            } else {
                for (n in ArrayList(it[0].phonetics)){

                    if (n.audio !== "") {
                        audioURl = n.audio
                        break
                    }
                }
                binding.apply {
                    wordSearched.text = it[0].word
                    sound.text = it[0].phonetic
                    meaningWordAdapter.submitList(it[0].meanings)
                    meaningList.visibility = View.VISIBLE
                    speaker.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    loadingText.visibility = View.GONE
                    lineText.visibility = View.VISIBLE
                    wordSearched.visibility = View.VISIBLE
                    sound.visibility = View.VISIBLE
                    save.visibility = View.VISIBLE
                    share.visibility = View.VISIBLE
                }
            }

        }

        binding.apply {
            save.setOnClickListener {
                viewModel.onSaveTracking(
                    0,
                    wordSearched.text.toString(),
                    it.context
                )
            }
            speaker.setOnClickListener {
                viewModel.mediaPlayer(audioURl)
                Toast.makeText(it.context,"Loading audio...",Toast.LENGTH_LONG).show()
            }
            share.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra("share this", viewModel.word)
                val chooser = Intent.createChooser(intent, "share using...")
                startActivity(chooser)
            }
        }
        return binding.root
    }
    private fun initRecyclerView() {
        binding.meaningList.apply {
            layoutManager = LinearLayoutManager(activity)
            meaningWordAdapter = MeaningWordAdapter(this@HomeFragment)
            adapter = meaningWordAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Meanings) {
    }
    private fun checkInput(it:String) {
        if (it.isEmpty()) {
            binding.textField.error = "No Entry"
        } else {

            binding.apply {
                progressBar.visibility = View.VISIBLE
                loadingText.visibility = View.VISIBLE
            }

            viewModel.onSearchWord(0, it)
            viewModel.getDefinition(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}



//    private fun initViewModel() {
//        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
//        viewModel.getResult().observe(viewLifecycleOwner) {
//            if (it == null) {
//                binding.apply {
//                    loadingText.visibility = View.GONE
//                    progressBar.visibility = View.GONE
//                    userError.visibility = View.VISIBLE
//                    userError.text = viewModel.userErrorTitle
//                }
//            } else {
//                for (n in ArrayList(it[0].phonetics)){
//
//                        if (n.audio !== "") {
//                            audioURl = n.audio
//                            break
//                    }
//                }
////                binding.apply {
////                    meaningList.visibility = View.VISIBLE
////                    speaker.visibility = View.VISIBLE
////                    progressBar.visibility = View.GONE
////                    loadingText.visibility = View.GONE
////                    wordSearched.text = it[0].word
////                    sound.text = it[0].phonetic
////                    lineText.visibility = View.VISIBLE
////                    meaningWordAdapter.submitList(it[0].meanings)
////                    wordSearched.visibility = View.VISIBLE
////                    sound.visibility = View.VISIBLE
////                    save.visibility = View.VISIBLE
////                    share.visibility = View.VISIBLE
////                }
//            }
//
//        }
//    }
//        sharedViewModel.wordSearched.observe(viewLifecycleOwner) {
//            viewModel.word = it.toString()
//            viewModel.getDefinition(it)
//        }