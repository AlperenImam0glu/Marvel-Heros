package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.R
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.adapter.CustomAttributeBarAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.ComicsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CreatorsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.SeriesPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.StoriesPagingAdapter
import com.example.marvelheroes.databinding.FragmentCharacterDetailPageBinding
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.stories.StoriesResults
import com.example.marvelheroes.viewmodel.DetailPageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailPageFragment : Fragment() {

    private lateinit var charactersData: CharactersResults
    private lateinit var comicsData: ComicsResults
    private lateinit var eventsData: EventsResults
    private lateinit var creatorsData: CreatorResults
    private lateinit var seriesData: SeriesResults
    private lateinit var storiesData: StoriesResults
    lateinit var binding: FragmentCharacterDetailPageBinding
    private val viewModelPaging: DetailPageViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var comicsAdapter: ComicsPagingAdapter
    lateinit var seriesAdapter: SeriesPagingAdapter
    lateinit var eventsAdapter: EventsPagingAdapter
    lateinit var storiesAdapter: StoriesPagingAdapter
    lateinit var charactersAdapter: CharacterPagingAdapter
    lateinit var creatorsAdapter: CreatorsPagingAdapter
    private var type: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicsAdapter = ComicsPagingAdapter(requireContext(), sharedViewModel)
        seriesAdapter = SeriesPagingAdapter(requireContext(), sharedViewModel)
        eventsAdapter = EventsPagingAdapter(requireContext(), sharedViewModel)
        storiesAdapter = StoriesPagingAdapter(requireContext(), sharedViewModel)
        charactersAdapter = CharacterPagingAdapter(requireContext(), sharedViewModel)
        creatorsAdapter = CreatorsPagingAdapter(requireContext(), sharedViewModel)

        arguments?.let {
            type = CharacterDetailPageFragmentArgs.fromBundle(it).type
        }

        createViewByType(type)

        setToolbarPosition()

        binding.toolbarBackBtn.setOnClickListener {
            findNavController(this).popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (type == 0) {
            var newList = sharedViewModel.getCharacter()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setCharacter(newList)
                }
            }
        }
        if (type == 1) {
            var newList = sharedViewModel.getComic()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setComic(newList)
                }
            }
        }
        if (type == 2) {
            var newList = sharedViewModel.getEvent()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setEvent(newList)
                }
            }
        }
        if (type == 3) {
            var newList = sharedViewModel.getCreators()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setCreators(newList)
                }
            }
        }
        if (type == 4) {
            var newList = sharedViewModel.getSeries()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setSeries(newList)
                }
            }
        }
        if (type == 5) {
            var newList = sharedViewModel.getStories()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setStories(newList)
                }
            }
        }
    }

    fun createViewByType(type: Int) {
        if (type == 0) {
            sharedViewModel.getCharacter()?.let {
                charactersData = it.last()
                viewModelPaging.id = charactersData.id.toString()
                setCharactersToView(charactersData)
                configureAbilitiesRecyclerView(
                    charactersData.comics!!.available,
                    charactersData.events!!.available,
                    charactersData.series!!.available,
                    charactersData.stories!!.available,
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(comicsAdapter, seriesAdapter, eventsAdapter, storiesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Comics", "Series", "Events", "Stories")
                setClickListeners(adapterList, stringList)
                initViewModelForCharacter()
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = comicsAdapter
            }
        } else if (type == 1) {
            sharedViewModel.getComic()?.let {
                comicsData = it.last()
                viewModelPaging.id = comicsData.id.toString()
                setComicsToView(comicsData)
                initViewModelForComics()
                configureAbilitiesRecyclerView(
                    comicsData.characters!!.available,
                    comicsData.events!!.available,
                    comicsData.creators!!.available,
                    comicsData.stories!!.available
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(charactersAdapter, creatorsAdapter, eventsAdapter, storiesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Characters", "Creators", "Events", "Stories")
                setClickListeners(adapterList, stringList)

                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = charactersAdapter
            }
        } else if (type == 2) {
            sharedViewModel.getEvent()?.let {
                eventsData = it.last()
                viewModelPaging.id = eventsData.id.toString()
                setEventToView(eventsData)
                initViewModelForEvents()
                configureAbilitiesRecyclerView(
                    eventsData.characters!!.available,
                    eventsData.series!!.available,
                    eventsData.creators!!.available,
                    eventsData.stories!!.available
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(charactersAdapter, creatorsAdapter, comicsAdapter, storiesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Characters", "Creators", "Comics", "Stories")
                setClickListeners(adapterList, stringList)
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = charactersAdapter
            }
        } else if (type == 3) {
            sharedViewModel.getCreators()?.let {
                creatorsData = it.last()
                viewModelPaging.id = creatorsData.id.toString()
                setCreatorToView(creatorsData)
                initViewModelForCreators()
                configureAbilitiesRecyclerView(
                    creatorsData.comics!!.available,
                    creatorsData.events!!.available,
                    creatorsData.series!!.available,
                    creatorsData.stories!!.available,
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(comicsAdapter, seriesAdapter, eventsAdapter, storiesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Comics", "Series", "Events", "Stories")
                setClickListeners(adapterList, stringList)

                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = comicsAdapter
            }
        } else if (type == 4) {
            sharedViewModel.getSeries()?.let {
                seriesData = it.last()
                viewModelPaging.id = seriesData.id.toString()
                setSeriesToView(seriesData)
                initViewModelForSeries()
                configureAbilitiesRecyclerView(
                    seriesData.characters!!.available,
                    seriesData.events!!.available,
                    seriesData.creators!!.available,
                    seriesData.stories!!.available
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(charactersAdapter, comicsAdapter, eventsAdapter, storiesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Characters", "Comics", "Events", "Stories")
                setClickListeners(adapterList, stringList)

                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = charactersAdapter
            }
        } else if (type == 5) {
            sharedViewModel.getStories()?.let {
                storiesData = it.last()
                viewModelPaging.id = storiesData.id.toString()
                setStoriesToView(storiesData)
                initViewModelForStories()
                configureAbilitiesRecyclerView(
                    storiesData.characters!!.available!!,
                    storiesData.events!!.available!!,
                    storiesData.comics!!.available!!,
                    storiesData.series!!.available!!
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(charactersAdapter, eventsAdapter, comicsAdapter, seriesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Characters", "Events", "Comics", "Series")
                setClickListeners(adapterList, stringList)

                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = charactersAdapter
            }
        }
    }

    fun setComicsToView(comicsData: ComicsResults) {
        if (comicsData.description.toString() != "") {
            binding.textView7.text = comicsData.description
        } else {
            binding.textView7.text = resources.getString(R.string.desc)
        }
        binding.textNameTitle.text = comicsData.title
        binding.textNameSubtitle.text = comicsData.id.toString()
        binding.textComicsCount.text = comicsData.characters!!.available.toString()
        binding.textEventsCount.text = comicsData.events!!.available.toString()
        binding.textSeriesCount.text = comicsData.creators!!.available.toString()
        binding.textStoriesCount.text = comicsData.stories!!.available.toString()
        binding.textComicsBar.text = "Characters"
        binding.textSeriesBar.text = "Creators"
        setImage(binding.image, comicsData.thumbnail!!.path!!, comicsData.thumbnail!!.extension!!)
    }

    fun setStoriesToView(storiesResults: StoriesResults) {
        if (storiesResults.description.toString() != "" && storiesResults.description != null) {
            binding.textView7.text = storiesResults.description
        } else {
            binding.textView7.text = resources.getString(R.string.desc)
        }

        binding.textNameTitle.text = storiesResults.title
        binding.textNameSubtitle.text = storiesResults.id.toString()
        binding.textComicsCount.text = storiesResults.characters!!.available.toString()
        binding.textEventsCount.text = storiesResults.events!!.available.toString()
        binding.textSeriesCount.text = storiesResults.comics!!.available.toString()
        binding.textStoriesCount.text = storiesResults.series!!.available.toString()

        if (storiesResults.thumbnail != null) {
            setImage(
                binding.image,
                storiesResults.thumbnail!!.path!!,
                storiesResults.thumbnail!!.extension!!
            )
        } else {
            setImage(binding.image, "", "")
        }

    }

    fun setSeriesToView(seriesResults: SeriesResults) {
        if (seriesResults.description.toString() != "" && seriesResults.description != null) {
            binding.textView7.text = seriesResults.description
        } else {
            binding.textView7.text = resources.getString(R.string.desc)
        }

        binding.textNameTitle.text = seriesResults.title
        binding.textNameSubtitle.text = seriesResults.id.toString()
        binding.textComicsCount.text = seriesResults.characters!!.available.toString()
        binding.textEventsCount.text = seriesResults.events!!.available.toString()
        binding.textSeriesCount.text = seriesResults.creators!!.available.toString()
        binding.textStoriesCount.text = seriesResults.stories!!.available.toString()
        binding.textComicsBar.text = "Characters"
        binding.textSeriesBar.text = "Creators"
        setImage(
            binding.image,
            seriesResults.thumbnail!!.path!!,
            seriesResults.thumbnail!!.extension!!
        )
    }

    fun setEventToView(eventsResults: EventsResults) {
        if (eventsResults.description.toString() != "") {
            binding.textView7.text = eventsResults.description
        } else {
            binding.textView7.text = resources.getString(R.string.desc)
        }
        binding.textNameTitle.text = eventsResults.title
        binding.textNameSubtitle.text = eventsResults.id.toString()
        binding.textComicsCount.text = eventsResults.characters!!.available.toString()
        binding.textEventsCount.text = eventsResults.series!!.available.toString()
        binding.textSeriesCount.text = eventsResults.creators!!.available.toString()
        binding.textStoriesCount.text = eventsResults.stories!!.available.toString()
        setImage(
            binding.image,
            eventsResults.thumbnail!!.path!!,
            eventsResults.thumbnail!!.extension!!
        )
    }

    fun setCreatorToView(creatorResults: CreatorResults) {
        binding.textNameTitle.text = creatorResults.fullName
        binding.textNameSubtitle.text = creatorResults.id.toString()
        binding.textComicsCount.text = creatorResults.comics!!.available.toString()
        binding.textEventsCount.text = creatorResults.events!!.available.toString()
        binding.textSeriesCount.text = creatorResults.series!!.available.toString()
        binding.textStoriesCount.text = creatorResults.stories!!.available.toString()
        setImage(
            binding.image,
            creatorResults.thumbnail!!.path!!,
            creatorResults.thumbnail!!.extension!!
        )
    }

    fun setCharactersToView(characterData: CharactersResults) {
        if (characterData.description.toString() != "") {
            binding.textView7.text = characterData.description
        } else {
            binding.textView7.text = resources.getString(R.string.desc)
        }
        binding.textNameTitle.text = characterData.name
        binding.textNameSubtitle.text = characterData.id.toString()
        binding.textComicsCount.text = characterData.comics!!.available.toString()
        binding.textEventsCount.text = characterData.events!!.available.toString()
        binding.textSeriesCount.text = characterData.series!!.available.toString()
        binding.textStoriesCount.text = characterData.stories!!.available.toString()
        setImage(
            binding.image,
            characterData.thumbnail!!.path!!,
            characterData.thumbnail!!.extension!!
        )
    }

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

    fun setClickListeners(adapterList: ArrayList<Any>, stringList: ArrayList<String>) {

        binding.img1.setOnClickListener {
            binding.rv.adapter = adapterList[0] as RecyclerView.Adapter<*>
            binding.rvTitle.text = stringList[0]
            scrollToRv()
        }
        binding.img2.setOnClickListener {
            binding.rv.adapter = adapterList[1] as RecyclerView.Adapter<*>
            binding.rvTitle.text = stringList[1]
            scrollToRv()
        }

        binding.img3.setOnClickListener {
            binding.rv.adapter = adapterList[2] as RecyclerView.Adapter<*>
            binding.rvTitle.text = stringList[2]
            scrollToRv()
        }

        binding.img4.setOnClickListener {
            binding.rv.adapter = adapterList[3] as RecyclerView.Adapter<*>
            binding.rvTitle.text = stringList[3]
            scrollToRv()
        }
    }

    fun configureAbilitiesRecyclerView(
        firstBarValue: Int?,
        secondBarValue: Int?,
        thirdValue: Int?,
        fourthBarValue: Int?
    ) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager4 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var adapter = CustomAttributeBarAdapter(firstBarValue ?: 0)
        binding.recyclerviewComics.layoutManager = layoutManager
        binding.recyclerviewComics.adapter = adapter

        var adapter2 = CustomAttributeBarAdapter(secondBarValue ?: 0)
        binding.recyclerviewSeries.layoutManager = layoutManager2
        binding.recyclerviewSeries.adapter = adapter2

        var adapter3 = CustomAttributeBarAdapter(thirdValue ?: 0)
        binding.recyclerviewEvents.layoutManager = layoutManager3
        binding.recyclerviewEvents.adapter = adapter3

        var adapter4 = CustomAttributeBarAdapter(fourthBarValue ?: 0)
        binding.recyclerviewStories.layoutManager = layoutManager4
        binding.recyclerviewStories.adapter = adapter4

    }

    fun setToolbarPosition() {
        //status barın yüksekliğini alıp toolbara ekliyoruz. Yoksa toolbar ekranın en tepesinden başlıyor
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        val param = binding.toolbar.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        binding.toolbar.layoutParams = param
    }

    fun scrollToRv() {
        binding.scrollView.smoothScrollBy(0, binding.rv.bottom)
    }

    fun setImage(view: ImageView, path: String, extension: String) {
        var url = path
        url += "." + extension
        var containsString = false

        url?.let {
            containsString = url!!.contains("image_not_available")
            if (path == "") {
                containsString = true
            }
        }

        if (containsString) {
            view.setBackgroundResource(R.drawable.portrait_xlarge)
        } else {
            Glide.with(requireContext())
                .load(url)
                .centerCrop()
                .into(view);
        }
    }

}