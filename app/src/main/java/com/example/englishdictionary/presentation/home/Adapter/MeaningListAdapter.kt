package com.example.englishdictionary.presentation.home.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.englishdictionary.databinding.MeaningItemListBinding
import com.example.englishdictionary.domain.model.WordInfo

class MeaningListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WordInfo>() {

        override fun areItemsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            TODO("not implemented")
        }

        override fun areContentsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            TODO("not implemented")
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MeaningListViewHolder(
            MeaningItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MeaningListViewHolder -> {
                holder.bind(differ.currentList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<WordInfo>) {
        differ.submitList(list)
    }

    class MeaningListViewHolder
    constructor(
        private val binding: MeaningItemListBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WordInfo, position: Int) = with(binding) {

            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: WordInfo)
    }
}