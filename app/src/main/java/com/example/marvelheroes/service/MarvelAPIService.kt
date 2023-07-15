package com.example.marvelheroes.service

import com.example.marvelheroes.Character
import com.example.marvelheroes.models.Comic
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MarvelAPIService {
    private val BASE_URL = "http://gateway.marvel.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarvelAPI::class.java)

    fun getData() : Call<Character> {
        return api.getAllCharacters()
    }
}