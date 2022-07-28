package com.example.englishdictionary.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishdictionary.databinding.HomeFragmentBinding
import com.example.englishdictionary.domain.model.Meaning
import com.example.englishdictionary.presentation.home.Adapter.MeaningListAdapter
import com.example.englishdictionary.presentation.main.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), MeaningListAdapter.Interaction {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var meaningListAdapter: MeaningListAdapter
//    private val viewModel: MainActivityViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(this.layoutInflater)
//        val state = viewModel.state.value
        initRecyclerView()
        binding.apply {
            textField.setEndIconOnClickListener {
                meaningList.visibility = View.GONE
                userError.visibility = View.GONE
                speaker.visibility = View.GONE
                lineText.visibility = View.GONE
                wordSearched.visibility = View.GONE
                phoneticText.visibility = View.GONE
                save.visibility = View.GONE
                share.visibility = View.GONE
//                viewModel.onSearch(textField.editText?.text.toString())
            }
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            viewModel.eventFlow.collectLatest { event ->
//                when (event) {
//                    is MainActivityViewModel.UIEvent.ShowSnackBar -> {
//                        showToast(event.message)
//                    }
//                }
//            }
//        }

        return binding.root
    }

    private fun initRecyclerView() {
        binding.meaningList.apply {
            layoutManager = LinearLayoutManager(activity)
            meaningListAdapter = MeaningListAdapter(this@HomeFragment)
            adapter = meaningListAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Meaning) {
        TODO("Not yet implemented")
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}