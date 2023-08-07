package com.example.marvelheroes.view.HomePage

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
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomePageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InitViewModelForHomePage(
    val viewModelPaging: HomePageViewModel, override val lifecycle: Lifecycle,
    var characterListAdapter: CharaterListAdapter,
    var comicsListAdapter: ComicsListAdapter,
    var creatorListAdapter: CreatorListAdapter,
    var eventListAdapter: EventListAdapter,
    var seriesListAdapter: SeriesListAdapter,
    var storiesListAdapter: StoriesListAdapter,
    val binding: FragmentHomePageBinding

) : LifecycleOwner {

    fun initViewModel() {
        lifecycleScope.launch {
            characterListAdapter.characterPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModelPaging.characterLoadingState.value = false
                }
            }
        }

        lifecycleScope.launch {
            comicsListAdapter.comicsPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModelPaging.comicsLoadingState.value = false
                }
            }
        }
        lifecycleScope.launch {
            creatorListAdapter.creatorsPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModelPaging.creatorsLoadingState.value = false
                }
            }
        }
        lifecycleScope.launch {
            eventListAdapter.evetPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModelPaging.eventsLoadingState.value = false
                }
            }
        }
        lifecycleScope.launch {
            seriesListAdapter.seriesPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModelPaging.seriesLoadingState.value = false
                }
            }
        }

        lifecycleScope.launch {
            storiesListAdapter.storiesPagingAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading) {
                    viewModelPaging.storiesLoadingState.value = false
                }
            }
        }

        lifecycleScope.launch {
            viewModelPaging.charactersData.collectLatest {
                characterListAdapter.characterPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.comicsData.collectLatest {
                comicsListAdapter.comicsPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.creatorsData.collectLatest {
                creatorListAdapter.creatorsPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.eventsData.collectLatest {
                eventListAdapter.evetPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.seriesData.collectLatest {
                seriesListAdapter.seriesPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.storiesData.collectLatest {
                storiesListAdapter.storiesPagingAdapter.submitData(it)
            }
        }
    }
}