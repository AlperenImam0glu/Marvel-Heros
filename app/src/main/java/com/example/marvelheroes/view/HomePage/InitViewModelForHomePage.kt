package com.example.marvelheroes.view.HomePage

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.marvelheroes.adapter.itemAdapters.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdapters.ComicsListAdapter
import com.example.marvelheroes.adapter.itemAdapters.CreatorListAdapter
import com.example.marvelheroes.adapter.itemAdapters.EventListAdapter
import com.example.marvelheroes.adapter.itemAdapters.SeriesListAdapter
import com.example.marvelheroes.adapter.itemAdapters.StoriesListAdapter
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
                    binding.shimmer.visibility = View.GONE
                    binding.homepageRv.visibility = View.VISIBLE
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