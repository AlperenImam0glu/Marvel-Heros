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

    @GET("v1/public/characters?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCharactersWithPage(@Query("offset") offset: Int): Character

    @GET("v1/public/comics?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllComicsWithPage(@Query("offset") offset: Int): MainComics

    @GET("v1/public/creators?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCreatorsWithPage(@Query("offset") offset: Int): MainCreators

    @GET("v1/public/events?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllEventsWithPage(@Query("offset") offset: Int): MainEvents

    @GET("v1/public/series?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllSeriesWithPage(@Query("offset") offset: Int): MainSeries

    @GET("v1/public/stories?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllStoriesWithPage(@Query("offset") offset: Int): MainStories

    @GET("v1/public/characters/{id}/comics?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllComicsOfCharacter(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("v1/public/characters/{id}/series?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllSeriesOfCharacter(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainSeries

    @GET("v1/public/characters/{id}/events?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllEventsOfCharacter(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("v1/public/characters/{id}/stories?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllStoriesOfCharacter(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("v1/public/comics/{id}/characters?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCharactersOfComic(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): Character

    @GET("v1/public/comics/{id}/creators?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCreatorsOfComics(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainCreators

    @GET("v1/public/comics/{id}/events?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllEventsOfComics(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainEvents
    @GET("v1/public/comics/{id}/stories?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllStoriesOfComics(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("v1/public/events/{id}/characters?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCharactersOfEvents(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): Character
    @GET("v1/public/events/{id}/comics?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllComicsOfEvents(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainComics
    @GET("v1/public/events/{id}/creators?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCreatorsOfEvents(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ):MainCreators
    @GET("v1/public/events/{id}/stories?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllStoriesOfEvents(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainStories

    @GET("v1/public/creators/{id}/comics?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllComicsOfCreators(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainComics

    @GET("v1/public/creators/{id}/stories?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllStoriesOfCreators(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainStories
    @GET("v1/public/creators/{id}/series?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllSeriesOfCreators(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainSeries
    @GET("v1/public/creators/{id}/events?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllEventsOfCreators(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainEvents

    @GET("v1/public/series/{id}/characters?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllCharactersOfSeries(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): Character
    @GET("v1/public/series/{id}/comics?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllComicsOfSeries(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainComics
    @GET("v1/public/series/{id}/events?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllEventsOfSeries(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainEvents
    @GET("v1/public/series/{id}/stories?ts=1&apikey=7532a6400c01747cfe46a0570dc1d611&hash=afdae4c1c3a8bc29dadf27415ba090f4")
    suspend fun getAllStoriesOfSeries(
        @Path("id") id: String,
        @Query("offset") offset: Int
    ): MainStories




}