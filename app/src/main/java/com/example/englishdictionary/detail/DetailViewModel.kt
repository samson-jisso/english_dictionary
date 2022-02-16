package com.example.englishdictionary.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.englishdictionary.network.RetroInterface
import com.example.englishdictionary.network.RetrofitInstance
import com.example.englishdictionary.network.dataClasses.DataResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    var searchedString: MutableLiveData<List<DataResult>?> = MutableLiveData()
    fun getResult(): MutableLiveData<List<DataResult>?> {
        return searchedString
    }

    fun getDefinition(word: String?) {
        val retroService = RetrofitInstance.getRetroInstance().create(RetroInterface::class.java)
        val call = retroService.searchString(word)
        call.enqueue(object : Callback<List<DataResult>> {
            override fun onFailure(call: Call<List<DataResult>>, t: Throwable) {
                searchedString.postValue(null)
            }

            override fun onResponse(
                call: Call<List<DataResult>>,
                response: Response<List<DataResult>>
            ) {
                if (response.isSuccessful) {
                    searchedString.postValue(response.body())
                } else {
                    searchedString.postValue(null)
                }
            }
        })
    }
}