package com.example.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.marvelheroes.paging.CharacterPagingSource
import com.example.marvelheroes.paging.ComicsPagingSource
import com.example.marvelheroes.paging.CreatorsPagingSource
import com.example.marvelheroes.paging.EventsPagingSource
import com.example.marvelheroes.paging.SeriesPagingSource
import com.example.marvelheroes.paging.StoriesPagingSource
import com.example.marvelheroes.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPageViewModel @Inject constructor(private val repository: MainRepository,)  : ViewModel() {

    var id ="0"
    val allComicsOfTheCharacter = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val allSeriesOfTheCharacter = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val allEventsOfTheCharacter = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val allStoriesOfTheCharacter = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val allCharacterOfTheComics = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val allCreatorsOfTheComics = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CreatorsPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val allEventsOfTheComics = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),2,id)
        }).flow.cachedIn(viewModelScope)

    val allStoriesOfTheComics = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),2,id)
        }).flow.cachedIn(viewModelScope)

    val allCharactersOfTheEvents = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(),2,id)
        }).flow.cachedIn(viewModelScope)

    val allComicsOfTheEvents = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),2,id)
        }).flow.cachedIn(viewModelScope)

    val allStoriesOfTheEvents = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),3,id)
        }).flow.cachedIn(viewModelScope)

    val allCreatorsOfTheEvents = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CreatorsPagingSource(repository.retroService(),2,id)
        }).flow.cachedIn(viewModelScope)

    val allEventsOfTheCreators = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),3,id)
        }).flow.cachedIn(viewModelScope)

    val allComicsOfTheCreators = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),3,id)
        }).flow.cachedIn(viewModelScope)

    val allStoriesOfTheCreators = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),4,id)
        }).flow.cachedIn(viewModelScope)

    val allSeriesOfTheCreators = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(),2,id)
        }).flow.cachedIn(viewModelScope)

    val allEventsOfTheSeries = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),4,id)
        }).flow.cachedIn(viewModelScope)

    val allComicsOfTheSeries = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),4,id)
        }).flow.cachedIn(viewModelScope)

    val allStoriesOfTheSeries= Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),5,id)
        }).flow.cachedIn(viewModelScope)

    val allCharactersOfTheSeries = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(),3,id)
        }).flow.cachedIn(viewModelScope)

    val allEventsOfTheStories = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),5,id)
        }).flow.cachedIn(viewModelScope)

    val allComicsOfTheStories = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),5,id)
        }).flow.cachedIn(viewModelScope)

    val allSeriesOfTheStories= Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(),3,id)
        }).flow.cachedIn(viewModelScope)

    val allCharactersOfTheStories = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(),4,id)
        }).flow.cachedIn(viewModelScope)

}