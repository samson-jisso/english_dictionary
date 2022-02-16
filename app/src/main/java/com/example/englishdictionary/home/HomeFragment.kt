package com.example.englishdictionary.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.englishdictionary.R
import com.example.englishdictionary.databinding.HomeFragmentBinding

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)

        binding.searchBtn.setOnClickListener {view:View ->
            viewModel.searchWord(binding.inputText.text.toString())
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
        return binding.root
    }


}