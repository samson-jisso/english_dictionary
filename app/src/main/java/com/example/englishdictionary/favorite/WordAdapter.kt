package com.example.englishdictionary.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.englishdictionary.R
import com.example.englishdictionary.db.SavedWords

class WordAdapter : RecyclerView.Adapter<TextViewHolder>() {
    var data = listOf<SavedWords>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = "${item.wordId}  ${item.word}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.fav_item_view, parent, false) as TextView
        return TextViewHolder(view)
    }
}