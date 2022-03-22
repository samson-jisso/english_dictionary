package com.example.englishdictionary.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.englishdictionary.databinding.DefinitionItemListBinding
import com.example.englishdictionary.network.dataClasses.Definitions
import com.example.englishdictionary.util.SharedViewModel

class DefinitionWordAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Definitions>() {

        override fun areItemsTheSame(oldItem: Definitions, newItem: Definitions): Boolean {
            return oldItem.definition == oldItem.definition
        }

        override fun areContentsTheSame(oldItem: Definitions, newItem: Definitions): Boolean {
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
                holder.bind(differ.currentList.get(position), position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Definitions>) {
        differ.submitList(list)
    }
    class DefinitionViewHolder
    constructor(
        private val binding: DefinitionItemListBinding,
        private val interaction: Interaction?,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val sharedViewModel = SharedViewModel()
        fun bind(item: Definitions, position: Int) = with(binding) {
            definition.text = item.definition
            example.text = item.example
            defAntonyms.text = item.antonyms.getOrNull(0)
            defSynonyms.text = item.synonyms.getOrNull(0)
            if (definition.text.isNotEmpty()){
                definitionText.visibility = View.VISIBLE
                definition.visibility = View.VISIBLE
            }
            if (example.text.isNotEmpty()){
                exampleText.visibility = View.VISIBLE
                example.visibility = View.VISIBLE
            }
            if (defAntonyms.text.isNotEmpty()){
                defAntonymsText.visibility = View.VISIBLE
                defAntonyms.visibility = View.VISIBLE
            }
            if (defSynonyms.text.toString().isNotEmpty()){
                defSynonymsText.visibility = View.VISIBLE
                defSynonyms.visibility = View.VISIBLE
            }
            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Definitions)
    }
}