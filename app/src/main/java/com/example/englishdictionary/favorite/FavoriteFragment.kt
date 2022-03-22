package com.example.englishdictionary.favorite

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishdictionary.databinding.FavoriteFragmentBinding
import com.example.englishdictionary.db.SavedWords
import com.example.englishdictionary.db.WordDatabase

class FavoriteFragment : Fragment(), FavoriteWordAdapter.Interaction {

    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var favoriteWordAdapter: FavoriteWordAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteFragmentBinding.inflate(layoutInflater)
        val application: Application = requireNotNull(this.activity).application
        val savedWordSource = WordDatabase.getInstance(application).savedWordsDao
        val viewModelFactory = FavoriteViewModelFactory(savedWordSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoriteViewModel::class.java]
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
                favoriteWordAdapter.submitList(it.sortedBy {
                    it.word
                })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}