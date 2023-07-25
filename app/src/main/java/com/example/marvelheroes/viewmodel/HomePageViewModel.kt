package com.example.marvelheroes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.ComicsData
import com.example.marvelheroes.MainComics
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.repository.MainRepository
import com.example.marvelheroes.service.MarvelAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageViewModel : ViewModel() {

    private val service = MarvelAPIService()
    val comicsList = MutableLiveData<ComicsData?>()
    private val repository = MainRepository()

    fun refreshData() {
        getComicsDataFromAPI()
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


    fun scrollToCharacter(binding: FragmentHomePageBinding) {
        repository.scrollToCharacter(binding)
    }

    fun scrollToComics(binding: FragmentHomePageBinding) {
        repository.scrollToComics(binding)
    }
}