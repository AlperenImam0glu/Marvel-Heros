package com.example.marvelheroes.repository

import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.paging.network.RetrofitApi
import com.example.marvelheroes.paging.network.RetrofitService
import javax.inject.Inject

class MainRepository  @Inject constructor(
    private val retrofitService: RetrofitService,
) {
    fun retroService(): RetrofitService =  retrofitService

}