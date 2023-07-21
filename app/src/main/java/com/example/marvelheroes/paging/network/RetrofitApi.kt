package com.example.marvelheroes.paging.network

import com.example.marvelheroes.service.MarvelAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    private val BASE_URL = "http://gateway.marvel.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

        val apiService : RetrofitService = retrofit.create(RetrofitService::class.java)

}