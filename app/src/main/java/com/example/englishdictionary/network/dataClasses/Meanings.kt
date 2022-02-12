package com.example.englishdictionary.network.dataClasses

import com.google.gson.annotations.SerializedName


data class Meanings (

  @SerializedName("partOfSpeech" ) var partOfSpeech : String?                = null,
  @SerializedName("definitions"  ) var definitions  : ArrayList<Definitions> = arrayListOf()
)