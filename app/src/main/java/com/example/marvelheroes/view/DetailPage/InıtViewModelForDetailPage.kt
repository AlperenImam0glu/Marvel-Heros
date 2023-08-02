package com.example.marvelheroes.view.DetailPage

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.ComicsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CreatorsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.SeriesPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.StoriesPagingAdapter
import com.example.marvelheroes.viewmodel.DetailPageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InıtViewModelForDetailPage(
    val viewModelPaging: DetailPageViewModel, override val lifecycle: Lifecycle,
    var comicsAdapter: ComicsPagingAdapter,
    var seriesAdapter: SeriesPagingAdapter,
    var eventsAdapter: EventsPagingAdapter,
    var storiesAdapter: StoriesPagingAdapter,
    var charactersAdapter: CharacterPagingAdapter,
    var creatorsAdapter: CreatorsPagingAdapter

) : LifecycleOwner {

    fun initViewModelForCharacter() {
        lifecycleScope.launch {
            viewModelPaging.allComicsOfTheCharacter.collectLatest {
                comicsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allSeriesOfTheCharacter.collectLatest {
                seriesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allEventsOfTheCharacter.collectLatest {
                eventsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allStoriesOfTheCharacter.collectLatest {
                storiesAdapter.submitData(it)
            }
        }

    }

    fun initViewModelForStories() {
        lifecycleScope.launch {
            viewModelPaging.allComicsOfTheStories.collectLatest {
                comicsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allSeriesOfTheStories.collectLatest {
                seriesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allEventsOfTheStories.collectLatest {
                eventsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allCharactersOfTheStories.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
    }

    fun initViewModelForCreators() {
        lifecycleScope.launch {
            viewModelPaging.allComicsOfTheCreators.collectLatest {
                comicsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allSeriesOfTheCreators.collectLatest {
                seriesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allEventsOfTheCreators.collectLatest {
                eventsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.allStoriesOfTheCreators.collectLatest {
                storiesAdapter.submitData(it)
            }
        }
    }

    fun initViewModelForComics() {
        lifecycleScope.launch {
            viewModelPaging.allCharacterOfTheComics.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allCreatorsOfTheComics.collectLatest {
                creatorsAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allEventsOfTheComics.collectLatest {
                eventsAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allStoriesOfTheComics.collectLatest {
                storiesAdapter.submitData(it)
            }
        }
    }

    fun initViewModelForSeries() {
        lifecycleScope.launch {
            viewModelPaging.allCharactersOfTheSeries.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allComicsOfTheSeries.collectLatest {
                comicsAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allEventsOfTheSeries.collectLatest {
                eventsAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allStoriesOfTheSeries.collectLatest {
                storiesAdapter.submitData(it)
            }
        }
    }

    fun initViewModelForEvents() {
        lifecycleScope.launch {
            viewModelPaging.allCharactersOfTheEvents.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allCreatorsOfTheEvents.collectLatest {
                creatorsAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allComicsOfTheEvents.collectLatest {
                comicsAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            viewModelPaging.allStoriesOfTheEvents.collectLatest {
                storiesAdapter.submitData(it)
            }
        }
    }

}