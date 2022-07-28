package com.example.englishdictionary.presentation.home.Adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.englishdictionary.databinding.MeaningItemListBinding
import com.example.englishdictionary.domain.model.Definition
import com.example.englishdictionary.domain.model.Meaning

class MeaningListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), DefinitionListAdapter.Interaction {
    private lateinit var definitionListAdapter: DefinitionListAdapter
    private lateinit var binding: MeaningItemListBinding
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Meaning>() {

        override fun areItemsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem.partOfSpeech == newItem.partOfSpeech
        }

        override fun areContentsTheSame(oldItem: Meaning, newItem: Meaning): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = MeaningItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningListViewHolder(
            binding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MeaningListViewHolder -> {
                val result = differ.currentList[position]
                binding.innerDefinition.apply {
                    layoutManager = LinearLayoutManager(holder.recycler.context)
                    definitionListAdapter = DefinitionListAdapter(this@MeaningListAdapter)
                    adapter = definitionListAdapter
                    definitionListAdapter.submitList(result.definitions)
                }
                holder.bind(result, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Meaning>) {
        differ.submitList(list)
    }

    class MeaningListViewHolder
    constructor(
        private val binding: MeaningItemListBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {
        val recycler = binding.innerDefinition
        fun bind(item: Meaning, position: Int) = with(binding) {
            partOfSpeech.text = item.partOfSpeech
            item.synonyms?.let {
                it.forEachIndexed { index, synonymsValue ->
                    synonyms.text = "${index + 1}. $synonymsValue"
                }
            }
            item.antonyms?.let {
                it.forEachIndexed { index, antonymsValue ->
                    antonyms.text = "${index + 1}. $antonymsValue"
                }
            }

            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Meaning)
    }

    override fun onItemSelected(position: Int, item: Definition) {
        TODO("Not yet implemented")
    }
}