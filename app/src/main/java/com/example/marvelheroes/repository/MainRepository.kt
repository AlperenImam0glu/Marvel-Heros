package com.example.marvelheroes.repository

import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.paging.network.RetrofitApi
import com.example.marvelheroes.paging.network.RetrofitService

class MainRepository {
    fun retroService(): RetrofitService =  RetrofitApi.apiService

    fun scrollToCharacter(binding: FragmentHomePageBinding){
        binding.scrollView.smoothScrollBy(0,binding.characterRecyclerView.top)
    }
    fun scrollToComics(binding: FragmentHomePageBinding){
        binding.scrollView.smoothScrollBy(0,binding.comicsRecyclerView.bottom)
    }
}