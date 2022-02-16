package com.example.englishdictionary.home

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishdictionary.network.dataClasses.DataResult

val viewModel = HomeViewModel()

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.DictionaryItemTextViewHolder>() {
    private var myList = listOf<DataResult>()
    inner class DictionaryItemTextViewHolder(textView: TextView) : RecyclerView.ViewHolder(textView)


    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: DictionaryItemTextViewHolder, position: Int) {
      val item = myList[position]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DictionaryItemTextViewHolder {
        TODO("Not yet implemented")
    }
}