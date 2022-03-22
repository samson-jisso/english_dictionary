package com.example.englishdictionary.history

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.contains
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishdictionary.databinding.HistoryFragmentBinding
import com.example.englishdictionary.databinding.HistoryWordItemListBinding
import com.example.englishdictionary.db.HistoryWords
import com.example.englishdictionary.db.WordDatabase
import com.example.englishdictionary.util.TopSpacingItemDecoration
import kotlin.math.absoluteValue

class HistoryFragment : Fragment(), HistoryWordAdapter.Interaction {

    private lateinit var viewModel: HistoryViewModel
    private var _binding: HistoryFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var historyWordAdapter: HistoryWordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryFragmentBinding.inflate(this.layoutInflater)
        val application: Application = requireNotNull(this.activity).application
        val historyWordSource = WordDatabase.getInstance(application).historyWordsDao
        val viewModelFactory = HistoryViewModelFactory(historyWordSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]
        initRecyclerView()
        viewModel.words.observe(viewLifecycleOwner) {
            it?.let {

                if (it.isEmpty()) {
                    binding.NoWord.visibility = View.VISIBLE
                }
                historyWordAdapter.submitList(it.sortedBy { it ->
                    it.word
                })
            }
        }
        binding.deleteAll.setOnClickListener {
            viewModel.deleteAllWord()
        }
        return binding.root
    }

    private fun initRecyclerView() {
        binding.historyWordList.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(5)
            addItemDecoration(topSpacingItemDecoration)
            historyWordAdapter = HistoryWordAdapter(this@HistoryFragment)
            adapter = historyWordAdapter
        }
    }

    override fun onItemSelected(position: Int, item: HistoryWords) {

    }

    override fun deleteWord(item: HistoryWords) {
        viewModel.deleteWord(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}