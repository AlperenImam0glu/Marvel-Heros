package com.example.marvelheroes.repository

import com.example.marvelheroes.paging.network.RetrofitApi
import com.example.marvelheroes.paging.network.RetrofitService

class MainRepository {
    fun retroService(): RetrofitService =  RetrofitApi.apiService
}