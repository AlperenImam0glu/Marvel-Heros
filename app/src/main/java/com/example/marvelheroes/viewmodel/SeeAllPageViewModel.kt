package com.example.marvelheroes.viewmodel

import androidx.lifecycle.MutableLiveData
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
import com.example.marvelheroes.util.Enums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject

@HiltViewModel
class SeeAllPageViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val PAGE_SIZE =30
    val dataLoadingState = MutableLiveData<Boolean>()
    var name= MutableLiveData<String>()
    val getAllCharacters = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

     val getCharactersWithName = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(), Enums.Search, name = name.value?:"")
        }).flow.cachedIn(viewModelScope)

    val getAllComics = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val getAllCreators = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CreatorsPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val getAllEvents = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val getAllSeries = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val getAllStories = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)
}