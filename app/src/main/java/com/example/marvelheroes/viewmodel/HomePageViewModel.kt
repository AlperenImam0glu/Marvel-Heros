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

import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    private val PAGE_SIZE = 30
    val isHeadTextOpen = MutableLiveData<Boolean>()
    val characterLoadingState = MutableLiveData<Boolean>()
    val comicsLoadingState = MutableLiveData<Boolean>()
    val eventsLoadingState = MutableLiveData<Boolean>()
    val seriesLoadingState = MutableLiveData<Boolean>()
    val storiesLoadingState = MutableLiveData<Boolean>()
    val creatorsLoadingState = MutableLiveData<Boolean>()


    val charactersData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val comicsData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val creatorsData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CreatorsPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val eventsData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val seriesData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)

    val storiesData = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)


}