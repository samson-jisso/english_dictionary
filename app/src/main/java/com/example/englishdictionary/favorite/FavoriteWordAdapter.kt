package com.example.englishdictionary.favorite

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.englishdictionary.databinding.FavItemViewBinding
import com.example.englishdictionary.db.SavedWords

class FavoriteWordAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SavedWords>() {

        override fun areItemsTheSame(oldItem: SavedWords, newItem: SavedWords): Boolean {
            return oldItem.wordId == newItem.wordId
        }

        override fun areContentsTheSame(oldItem: SavedWords, newItem: SavedWords): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return FavoriteWordViewHolder(
            FavItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoriteWordViewHolder -> {
                holder.bind(differ.currentList[position],position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<SavedWords>) {
        differ.submitList(list)
    }

    class FavoriteWordViewHolder
    constructor(
        private val binding: FavItemViewBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {
        val favWord = binding.favWord
        fun bind(item: SavedWords, position: Int) = with(binding) {
            favWord.text = item.word
            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
            deleteWord.setOnClickListener {
                interaction?.deleteWord(item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: SavedWords)
        fun deleteWord(item:SavedWords)
    }
}