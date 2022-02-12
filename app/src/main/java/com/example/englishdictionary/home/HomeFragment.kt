package com.example.englishdictionary.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.englishdictionary.databinding.HomeFragmentBinding
class HomeFragment : Fragment()  {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        initViewModel()
        binding.searchBtn.setOnClickListener{
            getDefination()
        }
        return binding.root
    }

    private fun getDefination() {
      val word= binding.inputText.text.toString()
        viewModel.getDefination(word)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getResult().observe(viewLifecycleOwner) {
            if(it== null) {
                println("no response")
            }else{
                val data = it[0].meanings[0].definitions[0].definition
                println(data)
            }

        }
    }


}