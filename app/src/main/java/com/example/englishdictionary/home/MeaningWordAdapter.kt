package com.example.englishdictionary.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.englishdictionary.databinding.MeaningItemListBinding
import com.example.englishdictionary.network.dataClasses.Definitions
import com.example.englishdictionary.network.dataClasses.Meanings

class MeaningWordAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), DefinitionWordAdapter.Interaction {
    private lateinit var definitionWordAdapter: DefinitionWordAdapter
    private lateinit var binding: MeaningItemListBinding
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Meanings>() {

        override fun areItemsTheSame(oldItem: Meanings, newItem: Meanings): Boolean {
            return oldItem.partOfSpeech == newItem.partOfSpeech
        }

        override fun areContentsTheSame(oldItem: Meanings, newItem: Meanings): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = MeaningItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(
            binding,
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DetailViewHolder -> {
                val result = differ.currentList[position]

                binding.innerDefinition.apply {
                    layoutManager = LinearLayoutManager(holder.recycler.context)
                    definitionWordAdapter = DefinitionWordAdapter(this@MeaningWordAdapter)
                    adapter = definitionWordAdapter
                    definitionWordAdapter.submitList(result.definitions)
                }
                holder.bind(result, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Meanings>) {

        differ.submitList(list)
    }

    class DetailViewHolder
    constructor(
        private val binding: MeaningItemListBinding,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(binding.root) {
        val recycler = binding.innerDefinition
        fun bind(item: Meanings, position: Int) = with(binding) {
            partOfSpeech.text = item.partOfSpeech
            partOfSpeechText.visibility = View.VISIBLE
            partOfSpeech.visibility = View.VISIBLE
            lineText.visibility = View.VISIBLE
            lineText2.visibility = View.VISIBLE
            innerDefinition.visibility = View.VISIBLE
            antonyms.text = item.antonyms.getOrNull(0)
            synonyms.text = item.synonyms.getOrNull(0)
            if (antonyms.text.isNotEmpty()) {
                antonymsText.visibility = View.VISIBLE
                antonyms.visibility = View.VISIBLE
            }
            if (synonyms.text.isNotEmpty()) {
                synonymsText.visibility = View.VISIBLE
                synonyms.visibility = View.VISIBLE
            }
            root.setOnClickListener {
                interaction?.onItemSelected(position, item)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Meanings)
    }

    override fun onItemSelected(position: Int, item: Definitions) {
    }
}