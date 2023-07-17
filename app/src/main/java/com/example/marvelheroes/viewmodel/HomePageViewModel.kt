package com.example.marvelheroes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.Character
import com.example.marvelheroes.ComicsData
import com.example.marvelheroes.MainComics
import com.example.marvelheroes.models.character.Data
import com.example.marvelheroes.service.MarvelAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageViewModel : ViewModel() {

    private val service = MarvelAPIService()
    val characterList = MutableLiveData<Data?>()
    val comicsList = MutableLiveData<ComicsData?>()

    fun refreshData() {
        getDataFromAPI()
        getComicsDataFromAPI()
    }

    private fun getDataFromAPI() {
        service.getAllCharacters().enqueue(object : Callback<Character> {
            override fun onResponse(
                call: Call<Character>,
                response: Response<Character>
            ) {
                if (response.isSuccessful) {
                    characterList.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.e("hata", t.message.toString())
            }
        })

    }

    private fun getComicsDataFromAPI() {
        service.getAllComics().enqueue(object : Callback<MainComics> {
            override fun onResponse(
                call: Call<MainComics>,
                response: Response<MainComics>
            ) {
                if (response.isSuccessful) {
                    comicsList.value = response.body()!!.data
                }
            }
            override fun onFailure(call: Call<MainComics>, t: Throwable) {
                Log.e("hata", t.message.toString())
            }
        })

    }
}