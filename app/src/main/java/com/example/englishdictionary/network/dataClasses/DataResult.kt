package com.example.englishdictionary.network.dataClasses

import com.google.gson.annotations.SerializedName


data class DataResult (

  @SerializedName("word"      ) var word      : String?              = null,
  @SerializedName("phonetic"  ) var phonetic  : String?              = null,
  @SerializedName("phonetics" ) var phonetics : ArrayList<Phonetics> = arrayListOf(),
  @SerializedName("origin"    ) var origin    : String?              = null,
  @SerializedName("meanings"  ) var meanings  : ArrayList<Meanings>  = arrayListOf()

)
