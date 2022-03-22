package com.example.englishdictionary.history

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.englishdictionary.databinding.HistoryWordItemListBinding
import com.example.englishdictionary.db.HistoryWords
import com.example.englishdictionary.db.HistoryWordsDao
import okio.blackholeSink
import kotlin.math.absoluteValue

lateinit var historyWordsDao: HistoryWordsDao
class HistoryWordAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryWords>() {

        override fun areItemsTheSame(oldItem: HistoryWords, newItem: HistoryWords): Boolean {
            return oldItem.wordId == newItem.wordId
        }

        override fun areContentsTheSame(oldItem: HistoryWords, newItem: HistoryWords): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryWordViewHolder(
            HistoryWordItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interaction,
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HistoryWordViewHolder -> {
                holder.bind(differ.currentList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<HistoryWords>) {
        differ.submitList(list)
    }

    class HistoryWordViewHolder
    constructor(
        private val binding: HistoryWordItemListBinding,
        private val interaction: Interaction?,
    ) : RecyclerView.ViewHolder(binding.root) {
        val wordItem = binding.WordItem

        fun bind(item: HistoryWords, position: Int) = with(binding) {

            wordItem.text = item.word
            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
            deleteWord.setOnClickListener {
                interaction?.deleteWord(item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: HistoryWords)
        fun deleteWord(item: HistoryWords)
    }
}
