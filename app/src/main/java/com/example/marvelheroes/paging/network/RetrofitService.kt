package com.example.marvelheroes.paging.network

import com.example.marvelheroes.Character
import com.example.marvelheroes.MainComics
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("v1/public/characters?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllCharactersWithPage(@Query("offset") offset : Int) : Character


    @GET("v1/public/characters?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    fun getAllCharacters() : Call<Character>

    @GET("v1/public/comics?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    fun getAllComics() : Call<MainComics>
}