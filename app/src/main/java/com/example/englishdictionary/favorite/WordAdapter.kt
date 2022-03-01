package com.example.englishdictionary.favorite

import androidx.recyclerview.widget.RecyclerView
import com.example.englishdictionary.db.SavedWords

class WordAdapter:RecyclerView.Adapter<TextViewHolder> {
    var data = listOf<SavedWords>()
    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {

    }
}