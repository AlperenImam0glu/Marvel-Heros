package com.example.marvelheroes.service

import com.example.marvelheroes.Character
import com.example.marvelheroes.MainComics
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelAPIService {

    private val BASE_URL = "http://gateway.marvel.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarvelAPI::class.java)

    fun getAllCharacters() : Call<Character> {
        return api.getAllCharacters()
    }
    fun getAllComics() : Call<MainComics> {
        return api.getAllComics()
    }

}