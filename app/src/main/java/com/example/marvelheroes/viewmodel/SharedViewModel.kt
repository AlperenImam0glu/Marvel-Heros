package com.example.marvelheroes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.stories.StoriesResults
import com.example.marvelheroes.util.Enums

class SharedViewModel : ViewModel() {


    private val _currentPage = MutableLiveData<Enums>()
    private val _character = MutableLiveData<ArrayList<CharactersResults>>()
    private val _comics = MutableLiveData<ArrayList<ComicsResults>>()
    private val _events = MutableLiveData<ArrayList<EventsResults>>()
    private val _series = MutableLiveData<ArrayList<SeriesResults>>()
    private val _stories = MutableLiveData<ArrayList<StoriesResults>>()
    private val _creators = MutableLiveData<ArrayList<CreatorResults>>()
    fun setCurrentPage(results: Enums) {
        _currentPage.value = results
    }
    fun getCurrentPage(): MutableLiveData<Enums>? = _currentPage

    fun setCharacter(results: ArrayList<CharactersResults>) {
        _character.value = results
    }
    fun getCharacter(): ArrayList<CharactersResults>? = _character.value

    fun setComic(results: ArrayList<ComicsResults>) {
        _comics.value = results
    }
    fun getComic(): ArrayList<ComicsResults>? = _comics.value

    fun setEvent(results: ArrayList<EventsResults>) {
        _events.value = results
    }
    fun getEvent(): ArrayList<EventsResults>? = _events.value

    fun setCreators(results: ArrayList<CreatorResults>) {
        _creators.value = results
    }
    fun getCreators(): ArrayList<CreatorResults>? = _creators.value

    fun setSeries(results: ArrayList<SeriesResults>) {
        _series.value = results
    }
    fun getSeries(): ArrayList<SeriesResults>? = _series.value

    fun setStories(results: ArrayList<StoriesResults>) {
        _stories.value = results
    }
    fun getStories(): ArrayList<StoriesResults>? = _stories.value


}