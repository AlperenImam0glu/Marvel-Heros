package com.example.marvelheroes.view.SeeAllPage

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.marvelheroes.adapter.itemAdaptersForConcat.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.ComicsListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.CreatorListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.EventListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.SeriesListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.StoriesListAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.ComicsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CreatorsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.SeriesPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.StoriesPagingAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.databinding.FragmentSeeAllPageBinding
import com.example.marvelheroes.viewmodel.SeeAllPageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InitViewModelForSeeAllPage (
    val viewModel: SeeAllPageViewModel, override val lifecycle: Lifecycle,
    var characterPagingAdapter: CharacterPagingAdapter,
    var comicsPagingAdapter: ComicsPagingAdapter,
    var creatorsPagingAdapter: CreatorsPagingAdapter,
    var eventsPagingAdapter: EventsPagingAdapter,
    var seriesPagingAdapter: SeriesPagingAdapter,
    var storiesPagingAdapter: StoriesPagingAdapter,
    val binding: FragmentSeeAllPageBinding

) : LifecycleOwner {

    fun getAllCharacters() {
        lifecycleScope.launch {
            viewModel.getAllCharacters.collectLatest {
                characterPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            characterPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModel.dataLoadingState.value = false
                }
            }
        }
    }

    fun getAllComics() {
        lifecycleScope.launch {
            viewModel.getAllComics.collectLatest {
                comicsPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            comicsPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModel.dataLoadingState.value = false
                }
            }
        }
    }

    fun getAllCreators() {
        lifecycleScope.launch {
            viewModel.getAllCreators.collectLatest {
                creatorsPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            creatorsPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModel.dataLoadingState.value = false
                }
            }
        }
    }
    fun getAllSeries() {
        lifecycleScope.launch {
            viewModel.getAllSeries.collectLatest {
                seriesPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            seriesPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModel.dataLoadingState.value = false
                }
            }
        }
    }
    fun getAllEvent() {
        lifecycleScope.launch {
            viewModel.getAllEvents.collectLatest {
                eventsPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            eventsPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModel.dataLoadingState.value = false
                }
            }
        }
    }
    fun getAllStories() {
        lifecycleScope.launch {
            viewModel.getAllStories.collectLatest {
                storiesPagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            storiesPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModel.dataLoadingState.value = false
                }
            }
        }
    }




}