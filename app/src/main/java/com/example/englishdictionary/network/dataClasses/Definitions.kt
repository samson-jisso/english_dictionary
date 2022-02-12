package com.example.englishdictionary.network.dataClasses

import com.google.gson.annotations.SerializedName


data class Definitions (

  @SerializedName("definition" ) var definition : String?           = null,
  @SerializedName("example"    ) var example    : String?           = null,
  @SerializedName("synonyms"   ) var synonyms   : ArrayList<String> = arrayListOf(),
  @SerializedName("antonyms"   ) var antonyms   : ArrayList<String> = arrayListOf()

)