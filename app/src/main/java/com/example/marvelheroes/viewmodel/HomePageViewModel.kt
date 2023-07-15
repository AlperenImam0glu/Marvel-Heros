package com.example.marvelheroes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.Character
import com.example.marvelheroes.Data
import com.example.marvelheroes.service.MarvelAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageViewModel : ViewModel() {

    private val service = MarvelAPIService()
    val characterList = MutableLiveData<Data?>()

    fun refreshData() {
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        service.getData().enqueue(object : Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ) {
                Log.v("hata", "şuan response içinde")
                if (response.isSuccessful) {
                    characterList.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.e("hata mesajı", t.message.toString())
            }
        })

    }


}