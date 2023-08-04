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

    @GET("characters")
    suspend fun getAllCharactersWithPage(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Character

    @GET("comics")
    suspend fun getAllComicsWithPage(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("creators")
    suspend fun getAllCreatorsWithPage(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainCreators

    @GET("events")
    suspend fun getAllEventsWithPage(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("series")
    suspend fun getAllSeriesWithPage(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainSeries

    @GET("stories")
    suspend fun getAllStoriesWithPage(
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("characters/{id}/comics")
    suspend fun getAllComicsOfCharacter(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("characters/{id}/series")
    suspend fun getAllSeriesOfCharacter(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainSeries

    @GET("characters/{id}/events")
    suspend fun getAllEventsOfCharacter(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("characters/{id}/stories")
    suspend fun getAllStoriesOfCharacter(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("comics/{id}/characters")
    suspend fun getAllCharactersOfComic(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Character

    @GET("comics/{id}/creators")
    suspend fun getAllCreatorsOfComics(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainCreators

    @GET("comics/{id}/events")
    suspend fun getAllEventsOfComics(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("comics/{id}/stories")
    suspend fun getAllStoriesOfComics(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("events/{id}/characters")
    suspend fun getAllCharactersOfEvents(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Character

    @GET("events/{id}/comics")
    suspend fun getAllComicsOfEvents(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("events/{id}/creators")
    suspend fun getAllCreatorsOfEvents(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainCreators

    @GET("events/{id}/stories")
    suspend fun getAllStoriesOfEvents(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("creators/{id}/comics")
    suspend fun getAllComicsOfCreators(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("creators/{id}/stories")
    suspend fun getAllStoriesOfCreators(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("creators/{id}/series")
    suspend fun getAllSeriesOfCreators(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainSeries

    @GET("creators/{id}/events")
    suspend fun getAllEventsOfCreators(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("series/{id}/characters")
    suspend fun getAllCharactersOfSeries(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Character

    @GET("series/{id}/comics")
    suspend fun getAllComicsOfSeries(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("series/{id}/events")
    suspend fun getAllEventsOfSeries(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("series/{id}/stories")
    suspend fun getAllStoriesOfSeries(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("stories/{id}/characters")
    suspend fun getAllCharactersOfStories(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): Character

    @GET("stories/{id}/comics")
    suspend fun getAllComicsOfStories(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("stories/{id}/events")
    suspend fun getAllEventsOfStories(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("stories/{id}/series")
    suspend fun getAllSeriesOfStories(
        @Path("id") id: String,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int
    ): MainSeries

}