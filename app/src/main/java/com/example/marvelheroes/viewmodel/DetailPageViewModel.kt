package com.example.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.marvelheroes.paging.ComicsPagingSource
import com.example.marvelheroes.paging.EventsPagingSource
import com.example.marvelheroes.paging.SeriesPagingSource
import com.example.marvelheroes.paging.StoriesPagingSource
import com.example.marvelheroes.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DenemeViewModel @Inject constructor(private val repository: MainRepository)  : ViewModel() {

    var id =""

    val comicsData = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            ComicsPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val seriesData = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            SeriesPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val eventsData = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            EventsPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

    val storiesData = Pager(config = PagingConfig(pageSize = 30),
        pagingSourceFactory = {
            StoriesPagingSource(repository.retroService(),1,id)
        }).flow.cachedIn(viewModelScope)

}