package com.example.englishdictionary.favorite

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englishdictionary.databinding.FavoriteFragmentBinding
import com.example.englishdictionary.db.SavedWords
import com.example.englishdictionary.db.WordDatabase
import com.example.englishdictionary.history.SwipeToDelete

class FavoriteFragment : Fragment(), FavoriteWordAdapter.Interaction {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var binding: FavoriteFragmentBinding
    private lateinit var favoriteWordAdapter: FavoriteWordAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(layoutInflater)
        val application: Application = requireNotNull(this.activity).application
        val savedWordSource = WordDatabase.getInstance(application).savedWordsDao
        val viewModelFactory = FavoriteViewModelFactory(savedWordSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
//        binding.wordList.layoutManager = LinearLayoutManager(requireContext())
//        val adapter = WordAdapter()
//        binding.wordList.adapter = adapter
        binding.deleteAll.setOnClickListener {
            viewModel.deleteAllWord()
        }
        intiRecyclerView()
        viewModel.words.observe(viewLifecycleOwner) {
            it?.let {
//                adapter.data = it
                if (it.isEmpty()) {
                    binding.NoWord.visibility = View.VISIBLE
                }
                favoriteWordAdapter.submitList(it)
            }
        }

        return binding.root
    }

    private fun intiRecyclerView() {
        binding.favList.apply {
            layoutManager = LinearLayoutManager(activity)
            favoriteWordAdapter = FavoriteWordAdapter(this@FavoriteFragment)
            adapter = favoriteWordAdapter
        }
    }

    override fun onItemSelected(position: Int, item: SavedWords) {

    }

    override fun deleteWord(item: SavedWords) {
        viewModel.deleteWord(item)
    }
}