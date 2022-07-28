package com.example.englishdictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.englishdictionary.data.util.JsonParser
import com.example.englishdictionary.domain.model.Meaning
import com.example.englishdictionary.domain.model.Phonetic
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromMeaningJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: "[]"
    }
    @TypeConverter
    fun fromPhoneticJson(json: String): List<Phonetic> {
        return jsonParser.fromJson<ArrayList<Phonetic>>(
            json,
            object : TypeToken<ArrayList<Phonetic>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toPhoneticJson(meanings: List<Phonetic>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Phonetic>>() {}.type
        ) ?: "[]"
    }
}