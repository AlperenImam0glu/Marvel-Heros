package com.example.marvelheroes.service

import com.example.marvelheroes.Character
import com.example.marvelheroes.models.Comic
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface MarvelAPI {
        //http://gateway.marvel.com/
        //v1/public/characters?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074

        @GET("v1/public/characters?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
        fun getAllCharacters() : Call<Character>


}