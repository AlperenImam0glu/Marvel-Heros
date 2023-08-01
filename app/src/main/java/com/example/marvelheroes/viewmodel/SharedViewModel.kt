package com.example.marvelheroes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.stories.StoriesResults

class SharedViewModel : ViewModel() {

    private val _character = MutableLiveData<ArrayList<CharactersResults>>()
    private val _comics = MutableLiveData<ArrayList<ComicsResults>>()
    private val _events = MutableLiveData<ArrayList<EventsResults>>()
    private val _series = MutableLiveData<SeriesResults>()
    private val _stories = MutableLiveData<StoriesResults>()
    private val _creators = MutableLiveData<ArrayList<CreatorResults>>()

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


}