package com.example.marvelheroes.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import com.example.marvelheroes.loadImageFromInternet
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.stories.StoriesResults
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.view.DetailPage.InıtViewModelForDetailPage
import com.example.marvelheroes.viewmodel.DetailPageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

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
    lateinit private var type: Enums
    lateinit var inıtViewModelForDetailPage: InıtViewModelForDetailPage

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

        inıtViewModelForDetailPage = InıtViewModelForDetailPage(
            viewModelPaging, lifecycle, comicsAdapter,
            seriesAdapter,
            eventsAdapter,
            storiesAdapter,
            charactersAdapter,
            creatorsAdapter
        )
        arguments?.let {
            type = CharacterDetailPageFragmentArgs.fromBundle(it).dataType
        }

        createViewByType(type)

        setToolbarPosition()

        binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.scrollView.scrollY > 1) {
                binding.toolbar.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.toolbar_shadow)

            }
            if (binding.scrollView.scrollY < 1) {
                binding.toolbar.background = null
                //  android:foreground="@drawable/gradient_dark"
            }
        }

        binding.toolbarBackBtn.setOnClickListener {
            findNavController(this).popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (type == Enums.Character) {
            var newList = sharedViewModel.getCharacter()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setCharacter(newList)
                }
            }
        }
        if (type == Enums.Comic) {
            var newList = sharedViewModel.getComic()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setComic(newList)
                }
            }
        }
        if (type == Enums.Event) {
            var newList = sharedViewModel.getEvent()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setEvent(newList)
                }
            }
        }
        if (type == Enums.Creator) {
            var newList = sharedViewModel.getCreators()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setCreators(newList)
                }
            }
        }
        if (type == Enums.Series) {
            var newList = sharedViewModel.getSeries()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setSeries(newList)
                }
            }
        }
        if (type == Enums.Story) {
            var newList = sharedViewModel.getStories()
            newList?.let {
                if (newList.isNotEmpty()) {
                    newList.removeLast()
                    sharedViewModel.setStories(newList)
                }
            }
        }
    }

    fun createViewByType(type: Enums) {
        if (type == Enums.Character) {
            sharedViewModel.getCharacter()?.let {
                charactersData = it.last()
                viewModelPaging.id = charactersData.id.toString()
                binding.toolbarTypeText.text = "Character"

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

                inıtViewModelForDetailPage.initViewModelForCharacter()

                if (charactersData.comics!!.available!! != 0) {
                    binding.rv.adapter = comicsAdapter
                } else {
                    binding.rv.visibility = View.GONE
                }
            }
        } else if (type == Enums.Comic) {
            sharedViewModel.getComic()?.let {
                comicsData = it.last()
                viewModelPaging.id = comicsData.id.toString()

                binding.toolbarTypeText.text = "Comic"
                setComicsToView(comicsData)
                inıtViewModelForDetailPage.initViewModelForComics()

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

                if (comicsData.characters!!.available!! != 0) {
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                }
            }
        } else if (type == Enums.Event) {

            sharedViewModel.getEvent()?.let {
                eventsData = it.last()
                binding.toolbarTypeText.text = "Event"
                viewModelPaging.id = eventsData.id.toString()
                setEventToView(eventsData)
                inıtViewModelForDetailPage.initViewModelForEvents()
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

                if (eventsData.characters!!.available!! != 0) {
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                }
            }
        } else if (type == Enums.Creator) {
            sharedViewModel.getCreators()?.let {
                creatorsData = it.last()

                binding.toolbarTypeText.text = "Creator"
                viewModelPaging.id = creatorsData.id.toString()
                setCreatorToView(creatorsData)

                inıtViewModelForDetailPage.initViewModelForCreators()
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

                if (creatorsData.comics!!.available!! != 0) {
                    binding.rv.adapter = comicsAdapter
                } else {
                    binding.rv.visibility = View.GONE
                }
            }
        } else if (type == Enums.Series) {
            sharedViewModel.getSeries()?.let {
                seriesData = it.last()
                binding.toolbarTypeText.text = "Series"
                viewModelPaging.id = seriesData.id.toString()
                setSeriesToView(seriesData)
                inıtViewModelForDetailPage.initViewModelForSeries()
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

                if (seriesData.characters!!.available!! != 0) {
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                }
            }
        } else if (type == Enums.Story) {
            sharedViewModel.getStories()?.let {
                storiesData = it.last()
                binding.toolbarTypeText.text = "Story"
                viewModelPaging.id = storiesData.id.toString()
                setStoriesToView(storiesData)
                inıtViewModelForDetailPage.initViewModelForStories()
                configureAbilitiesRecyclerView(
                    storiesData.characters!!.available!!,
                    storiesData.events!!.available!!,
                    storiesData.comics!!.available!!,
                    storiesData.series!!.available!!
                )
                val adapterList: ArrayList<Any> =
                    arrayListOf(charactersAdapter, comicsAdapter, eventsAdapter, seriesAdapter)
                val stringList: ArrayList<String> =
                    arrayListOf("Characters", "Events", "Comics", "Series")
                setClickListeners(adapterList, stringList)

                if (storiesData.characters!!.available!! != 0) {
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                }

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

    fun setClickListeners(adapterList: ArrayList<Any>, stringList: ArrayList<String>) {


        binding.img1.setOnClickListener {
            if (binding.textComicsCount.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[0] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[0]
                scrollToRv()
            }
        }
        binding.img2.setOnClickListener {
            if (binding.textSeriesCount.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[1] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[1]
                scrollToRv()
            }
        }

        binding.img3.setOnClickListener {
            if (binding.textEventsCount.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[2] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[2]
                scrollToRv()
            }
        }

        binding.img4.setOnClickListener {
            if (binding.textStoriesCount.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[3] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[3]
                scrollToRv()
            }
        }
    }

    fun configureAbilitiesRecyclerView(
        firstBarValue: Int?,
        secondBarValue: Int?,
        thirdValue: Int?,
        fourthBarValue: Int?
    ) {
        binding.recyclerviewComics.adapter = CustomAttributeBarAdapter(firstBarValue ?: 0)
        binding.recyclerviewSeries.adapter = CustomAttributeBarAdapter(thirdValue ?: 0)
        binding.recyclerviewEvents.adapter = CustomAttributeBarAdapter(secondBarValue ?: 0)
        binding.recyclerviewStories.adapter = CustomAttributeBarAdapter(fourthBarValue ?: 0)
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
        view.loadImageFromInternet(url!!, view)
    }

}