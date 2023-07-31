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
import com.bumptech.glide.Glide
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.R
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.ComicsData
import com.example.marvelheroes.adapter.CustomAttributeBarAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.ComicsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CreatorsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.SeriesPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.StoriesPagingAdapter
import com.example.marvelheroes.databinding.FragmentCharacterDetailPageBinding
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.viewmodel.DetailPageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailPageFragment : Fragment() {

    private lateinit var charactersData: CharactersResults
    private lateinit var comicsData: ComicsResults
    private lateinit var eventsData:EventsResults
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
        seriesAdapter = SeriesPagingAdapter(requireContext())
        eventsAdapter = EventsPagingAdapter(requireContext(),sharedViewModel)
        storiesAdapter = StoriesPagingAdapter(requireContext())
        charactersAdapter = CharacterPagingAdapter(requireContext(), sharedViewModel)
        creatorsAdapter = CreatorsPagingAdapter(requireContext())

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
            var tmp = sharedViewModel.getCharacter()
            tmp?.let {
                if (tmp.isNotEmpty()) {
                    tmp.removeLast()
                    sharedViewModel.setCharacter(tmp)
                }
            }
        }
        if (type == 1) {
            var tmp = sharedViewModel.getComic()
            tmp?.let {
                if (tmp.isNotEmpty()) {
                    tmp.removeLast()
                    sharedViewModel.setComic(tmp)
                }
            }
        }
        if (type == 2) {
            var tmp = sharedViewModel.getEvent()
            tmp?.let {
                if (tmp.isNotEmpty()) {
                    tmp.removeLast()
                    sharedViewModel.setEvent(tmp)
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
                initViewModelForCharacter()
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = comicsAdapter

            }

        } else if(type ==1) {
            sharedViewModel.getComic()?.let {
                comicsData = it.last()
                viewModelPaging.id = comicsData.id.toString()
                setComicsToView(comicsData)
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = charactersAdapter
            }
        }

        else if(type ==2) {
            sharedViewModel.getEvent()?.let {
                eventsData = it.last()
                viewModelPaging.id = eventsData.id.toString()
                setEventToView(eventsData)
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
        initViewModelForComics()
        binding.textNameTitle.text = comicsData.title
        binding.textNameSubtitle.text = comicsData.id.toString()
        binding.textComicsCount.text = comicsData.characters!!.available.toString()
        binding.textEventsCount.text = comicsData.events!!.available.toString()
        binding.textSeriesCount.text = comicsData.creators!!.available.toString()
        binding.textStoriesCount.text = comicsData.stories!!.available.toString()
        binding.textComicsBar.text="Characters"
        binding.textSeriesBar.text="Creators"
        setImage(binding.image, comicsData.thumbnail!!.path!!, comicsData.thumbnail!!.extension!!)
        configureRecyclerViewComics(comicsData)
        setClickListenersComics()
    }

    fun setEventToView(eventsResults: EventsResults) {

        initViewModelForEvents()
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
        setImage(binding.image, eventsResults.thumbnail!!.path!!, eventsResults.thumbnail!!.extension!!)
        configureRecyclerViewEvent(eventsResults)
        setClickListenersEvents()
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
        configureRecyclerViewCharacters(characterData)
        setClickListenersCharacters()
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

    fun setClickListenersComics() {

        binding.img1.setOnClickListener {
            binding.rv.adapter = charactersAdapter
            binding.rvTitle.text = "Characters"
            scrollToRv()
        }
        binding.img2.setOnClickListener {
            binding.rv.adapter = creatorsAdapter
            binding.rvTitle.text = "Creators"
            scrollToRv()
        }

        binding.img3.setOnClickListener {
            binding.rv.adapter = eventsAdapter
            binding.rvTitle.text = "Events"
            scrollToRv()
        }

        binding.img4.setOnClickListener {
            binding.rv.adapter = storiesAdapter
            binding.rvTitle.text = "Stories"
            scrollToRv()
        }
    }


    fun setClickListenersEvents() {

        binding.img1.setOnClickListener {
            binding.rv.adapter = charactersAdapter
            binding.rvTitle.text = "Characters"
            scrollToRv()
        }
        binding.img2.setOnClickListener {
            binding.rv.adapter = creatorsAdapter
            binding.rvTitle.text = "Creators"
            scrollToRv()
        }

        binding.img3.setOnClickListener {
            binding.rv.adapter = comicsAdapter
            binding.rvTitle.text = "Comics"
            scrollToRv()
        }

        binding.img4.setOnClickListener {
            binding.rv.adapter = storiesAdapter
            binding.rvTitle.text = "Stories"
            scrollToRv()
        }
    }

    fun setClickListenersCharacters() {

        binding.img1.setOnClickListener {
            binding.rv.adapter = comicsAdapter
            binding.rvTitle.text = "Comics"
            scrollToRv()
        }
        binding.img2.setOnClickListener {
            binding.rv.adapter = seriesAdapter
            binding.rvTitle.text = "Series"
            scrollToRv()
        }

        binding.img3.setOnClickListener {
            binding.rv.adapter = eventsAdapter
            binding.rvTitle.text = "Events"
            scrollToRv()
        }

        binding.img4.setOnClickListener {
            binding.rv.adapter = storiesAdapter
            binding.rvTitle.text = "Stories"
            scrollToRv()
        }
    }

    fun configureRecyclerViewCharacters(characterData: CharactersResults) {

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager4 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var adapter = CustomAttributeBarAdapter(characterData.comics!!.available ?: 0)
        binding.recyclerviewComics.layoutManager = layoutManager
        binding.recyclerviewComics.adapter = adapter

        var adapter2 = CustomAttributeBarAdapter(characterData.series!!.available ?: 0)
        binding.recyclerviewSeries.layoutManager = layoutManager2
        binding.recyclerviewSeries.adapter = adapter2

        var adapter3 = CustomAttributeBarAdapter(characterData.events!!.available ?: 0)
        binding.recyclerviewEvents.layoutManager = layoutManager3
        binding.recyclerviewEvents.adapter = adapter3

        var adapter4 = CustomAttributeBarAdapter(characterData.stories!!.available ?: 0)
        binding.recyclerviewStories.layoutManager = layoutManager4
        binding.recyclerviewStories.adapter = adapter4

    }

    fun configureRecyclerViewComics(comicsData: ComicsResults) {

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager4 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var adapter = CustomAttributeBarAdapter(comicsData.characters!!.available ?: 0)
        binding.recyclerviewComics.layoutManager = layoutManager
        binding.recyclerviewComics.adapter = adapter

        var adapter2 = CustomAttributeBarAdapter(comicsData.creators!!.available ?: 0)
        binding.recyclerviewSeries.layoutManager = layoutManager2
        binding.recyclerviewSeries.adapter = adapter2

        var adapter3 = CustomAttributeBarAdapter(comicsData.events!!.available ?: 0)
        binding.recyclerviewEvents.layoutManager = layoutManager3
        binding.recyclerviewEvents.adapter = adapter3

        var adapter4 = CustomAttributeBarAdapter(comicsData.stories!!.available ?: 0)
        binding.recyclerviewStories.layoutManager = layoutManager4
        binding.recyclerviewStories.adapter = adapter4


    }

    fun configureRecyclerViewEvent(eventResult: EventsResults) {

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager4 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var adapter = CustomAttributeBarAdapter(eventResult.characters!!.available ?: 0)
        binding.recyclerviewComics.layoutManager = layoutManager
        binding.recyclerviewComics.adapter = adapter

        var adapter2 = CustomAttributeBarAdapter(eventResult.creators!!.available ?: 0)
        binding.recyclerviewSeries.layoutManager = layoutManager2
        binding.recyclerviewSeries.adapter = adapter2

        var adapter3 = CustomAttributeBarAdapter(eventResult.series!!.available ?: 0)
        binding.recyclerviewEvents.layoutManager = layoutManager3
        binding.recyclerviewEvents.adapter = adapter3

        var adapter4 = CustomAttributeBarAdapter(eventResult.stories!!.available ?: 0)
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