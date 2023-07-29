package com.example.marvelheroes.paging.network

import com.example.marvelheroes.Character
import com.example.marvelheroes.MainComics
import com.example.marvelheroes.MainCreators
import com.example.marvelheroes.models.MainEvents
import com.example.marvelheroes.series.MainSeries
import com.example.marvelheroes.stories.MainStories
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("v1/public/characters?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllCharactersWithPage(@Query("offset") offset: Int): Character

    @GET("v1/public/comics?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllComicsWithPage(@Query("offset") offset: Int): MainComics

    @GET("v1/public/creators?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllCreatorsWithPage(@Query("offset") offset: Int): MainCreators

    @GET("v1/public/events?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllEventsWithPage(@Query("offset") offset: Int): MainEvents

    @GET("v1/public/series?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllSeriesWithPage(@Query("offset") offset: Int): MainSeries

    @GET("v1/public/stories?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllStoriesWithPage(@Query("offset") offset: Int): MainStories

    @GET("v1/public/characters/{id}/comics?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllComicsWithId(@Path("id") id: String, @Query("offset") offset: Int): MainComics

    @GET("v1/public/characters/{id}/series?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllSeriesWithId(@Path("id") id: String, @Query("offset") offset: Int): MainSeries

    @GET("v1/public/characters/{id}/events?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllEventsWithId(@Path("id") id: String, @Query("offset") offset: Int): MainEvents

    @GET("v1/public/characters/{id}/stories?ts=1&apikey=e54bad6078fdfcb7a1fdbc479f3391de&hash=b5e74f65be214f7d6aa9c8c89f06f074")
    suspend fun getAllStoriesWithId(@Path("id") id: String, @Query("offset") offset: Int): MainStories


}