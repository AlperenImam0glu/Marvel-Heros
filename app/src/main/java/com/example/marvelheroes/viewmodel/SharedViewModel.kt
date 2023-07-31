package com.example.marvelheroes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.Events
import com.example.marvelheroes.Results
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.stories.StoriesResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {

    private val _character = MutableLiveData<ArrayList<Results>>()

    private val _comics = MutableLiveData<ComicsResults?>()
    private val _creators = MutableLiveData<CreatorResults>()
    private val _events = MutableLiveData<EventsResults>()
    private val _series = MutableLiveData<SeriesResults>()
    private val _stories = MutableLiveData<StoriesResults>()

    fun setCharacter(results: ArrayList<Results>) {
        _character.value = results
    }
    fun getCharacter(): ArrayList<Results>?{
        return _character.value
    }

    fun setComics(results: ComicsResults?) {
        _comics.value = results
    }
    fun getComics(): ComicsResults?{
        return _comics.value
    }
}