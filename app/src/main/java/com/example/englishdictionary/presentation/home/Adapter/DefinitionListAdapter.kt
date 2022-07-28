package com.example.englishdictionary.presentation.home.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.englishdictionary.databinding.DefinitionItemListBinding
import com.example.englishdictionary.domain.model.Definition

class DefinitionListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Definition>() {

        override fun areItemsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem.definition == newItem.definition
        }

        override fun areContentsTheSame(oldItem: Definition, newItem: Definition): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DefinitionViewHolder(
            DefinitionItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DefinitionViewHolder -> {
                holder.bind(differ.currentList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Definition>) {
        differ.submitList(list)
    }

    class DefinitionViewHolder
    constructor(
        private val binding: DefinitionItemListBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Definition, position: Int) = with(binding) {
            definition.text = item.definition
            item.synonyms?.let {
                it.forEachIndexed { index, synonymString ->
                    defSynonyms.text = "${index + 1}. $synonymString"
                }
            }
            item.antonyms?.let {
                it.forEachIndexed { index, antonymString ->
                    defSynonyms.text = "${index + 1}. $antonymString"
                }
            }
            example.text = item.example
            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Definition)
    }
}