package com.example.marvelheroes.service

import com.example.marvelheroes.Character
import com.example.marvelheroes.MainComics
import com.example.marvelheroes.paging.network.RetrofitService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelAPIService {

    private val BASE_URL = "http://gateway.marvel.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)

    fun getAllComics() : Call<MainComics> {
        return api.getAllComics()
    }

}