package com.example.marvelheroes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.marvelheroes.paging.CharacterPagingSource
import com.example.marvelheroes.repository.MainRepository
import com.example.marvelheroes.util.Enums
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeeAllPageViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val PAGE_SIZE =30

    val getAllCharacters = Pager(config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            CharacterPagingSource(repository.retroService(), Enums.Home)
        }).flow.cachedIn(viewModelScope)
}