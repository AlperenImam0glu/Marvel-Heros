package com.example.marvelheroes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.paging.CharacterPagingSource
import com.example.marvelheroes.paging.ComicsPagingSource
import com.example.marvelheroes.paging.CreatorsPagingSource
import com.example.marvelheroes.paging.EventsPagingSource
import com.example.marvelheroes.paging.SeriesPagingSource
import com.example.marvelheroes.paging.StoriesPagingSource
import com.example.marvelheroes.repository.MainRepository
import com.example.marvelheroes.util.Enums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.subscribe

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository)  : ViewModel() {

    val characterLoading = MutableLiveData<Boolean>()
    val comicLoading = MutableLiveData<Boolean>()
    val creatorLoading = MutableLiveData<Boolean>()
    val eventLoading = MutableLiveData<Boolean>()
    val seriesLoading = MutableLiveData<Boolean>()
    val storiesLoading = MutableLiveData<Boolean>()

    val isOpen = MutableLiveData<Boolean>()


   val charactersData = Pager(config = PagingConfig(pageSize = 30),
    pagingSourceFactory = {
        CharacterPagingSource(repository.retroService(),Enums.Home)
    }).flow.cachedIn(viewModelScope)


    val comicsData = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val creatorsData  = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            CreatorsPagingSource(repository.retroService(),Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val eventsData  = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val seriesData  = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(),Enums.Home )
        }).flow.cachedIn(viewModelScope)

    val storiesData  = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),Enums.Home)
        }).flow.cachedIn(viewModelScope)



}