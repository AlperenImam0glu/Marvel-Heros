package com.example.marvelheroes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.marvelheroes.ComicsData
import com.example.marvelheroes.MainComics
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.paging.HomePagingSource
import com.example.marvelheroes.repository.MainRepository
import com.example.marvelheroes.service.MarvelAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {


   private val repository = MainRepository()

    val homePage = Pager(config = PagingConfig(pageSize = 30),
    pagingSourceFactory = {
        HomePagingSource(repository.retroService())
    }).flow.cachedIn(viewModelScope)











    private val service = MarvelAPIService()
    val comicsList = MutableLiveData<ComicsData?>()

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
        binding.scrollView.smoothScrollBy(0,binding.characterRecyclerView.top)
    }

    fun scrollToComics(binding: FragmentHomePageBinding) {
        binding.scrollView.smoothScrollBy(0,binding.comicsRecyclerView.bottom)
    }

}