package com.example.marvelheroes.view.DetailPage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.R
import com.example.marvelheroes.adapter.customAdapters.CustomAttributeBarAdapter
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
import com.example.marvelheroes.viewmodel.DetailPageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPageFragment : Fragment() {

    private lateinit var charactersData: CharactersResults
    private lateinit var comicsData: ComicsResults
    private lateinit var eventsData: EventsResults
    private lateinit var creatorsData: CreatorResults
    private lateinit var seriesData: SeriesResults
    private lateinit var storiesData: StoriesResults
    lateinit var binding: FragmentCharacterDetailPageBinding
    private val detailPageViewModel: DetailPageViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var comicsAdapter: ComicsPagingAdapter
    lateinit var seriesAdapter: SeriesPagingAdapter
    lateinit var eventsAdapter: EventsPagingAdapter
    lateinit var storiesAdapter: StoriesPagingAdapter
    lateinit var charactersAdapter: CharacterPagingAdapter
    lateinit var creatorsAdapter: CreatorsPagingAdapter
    lateinit private var type: Enums
    lateinit var inıtViewModelForDetailPage: InıtViewModelForDetailPage
    private val noDataString = "No data to show"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            WindowCompat.getInsetsController(it.window, it.window.decorView).apply {
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                hide(WindowInsetsCompat.Type.statusBars())
            }

            it.window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );

        }




        binding = FragmentCharacterDetailPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comicsAdapter = ComicsPagingAdapter(requireContext(), sharedViewModel)
        seriesAdapter = SeriesPagingAdapter(requireContext(), sharedViewModel)
        eventsAdapter = EventsPagingAdapter(requireContext(), sharedViewModel)
        storiesAdapter = StoriesPagingAdapter(requireContext(), sharedViewModel)
        charactersAdapter = CharacterPagingAdapter(requireContext(), sharedViewModel)
        creatorsAdapter = CreatorsPagingAdapter(requireContext(), sharedViewModel)


        inıtViewModelForDetailPage = InıtViewModelForDetailPage(
            detailPageViewModel, lifecycle, comicsAdapter,
            seriesAdapter,
            eventsAdapter,
            storiesAdapter,
            charactersAdapter,
            creatorsAdapter
        )

        arguments?.let {
            type = DetailPageFragmentArgs.fromBundle(it).dataType
        }

        createViewByType(type)

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
                detailPageViewModel.id = charactersData.id.toString()
                binding.toolbarText.text = "Character"

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
                    binding.sizedBox.visibility = View.VISIBLE
                    binding.rvTitle.text = noDataString
                }
            }
        } else if (type == Enums.Comic) {
            sharedViewModel.getComic()?.let {
                comicsData = it.last()
                detailPageViewModel.id = comicsData.id.toString()

                binding.toolbarText.text = "Comic"
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
                    binding.rvTitle.text = "Characters"
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                    binding.sizedBox.visibility = View.VISIBLE
                    binding.rvTitle.text = noDataString
                }
            }
        } else if (type == Enums.Event) {

            sharedViewModel.getEvent()?.let {
                eventsData = it.last()
                binding.toolbarText.text = "Event"
                detailPageViewModel.id = eventsData.id.toString()
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
                    binding.rvTitle.text = "Characters"
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                    binding.sizedBox.visibility = View.VISIBLE
                    binding.rvTitle.text = noDataString
                }
            }
        } else if (type == Enums.Creator) {
            sharedViewModel.getCreators()?.let {
                creatorsData = it.last()

                binding.toolbarText.text = "Creator"
                detailPageViewModel.id = creatorsData.id.toString()
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
                    binding.rvTitle.text = "Comics"
                    binding.rv.adapter = comicsAdapter
                } else {
                    binding.rv.visibility = View.GONE
                    binding.sizedBox.visibility = View.VISIBLE
                    binding.rvTitle.text = noDataString
                }
            }
        } else if (type == Enums.Series) {
            sharedViewModel.getSeries()?.let {
                seriesData = it.last()
                binding.toolbarText.text = "Series"
                detailPageViewModel.id = seriesData.id.toString()
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
                    binding.rvTitle.text = "Characters"
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                    binding.sizedBox.visibility = View.VISIBLE
                    binding.rvTitle.text = noDataString
                }
            }
        } else if (type == Enums.Story) {
            sharedViewModel.getStories()?.let {
                storiesData = it.last()
                binding.toolbarText.text = "Story"
                detailPageViewModel.id = storiesData.id.toString()
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
                    arrayListOf("Characters", "Comics", "Events", "Series")
                setClickListeners(adapterList, stringList)

                if (storiesData.characters!!.available!! != 0) {
                    binding.rvTitle.text = "Characters"
                    binding.rv.adapter = charactersAdapter
                } else {
                    binding.rv.visibility = View.GONE
                    binding.sizedBox.visibility = View.VISIBLE
                    binding.rvTitle.text = noDataString
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setCurrentPage(Enums.Detail)
    }
    fun setComicsToView(comicsData: ComicsResults) {
        if (comicsData.description.toString() != "") {
            binding.textDescription.text = comicsData.description
        } else {
            binding.textDescription.text = resources.getString(R.string.desc)
        }
        binding.textNameTitle.text = comicsData.title
        binding.textNameSubtitle.text = comicsData.id.toString()
        binding.textImg1.text = comicsData.characters!!.available.toString()
        binding.textImg3.text = comicsData.events!!.available.toString()
        binding.textImg2.text = comicsData.creators!!.available.toString()
        binding.textImg4.text = comicsData.stories!!.available.toString()
        binding.abilitiesText1.text = "Characters"
        binding.abilitiesText2.text = "Creators"
        setImage(binding.pageBackgroundImage, comicsData.thumbnail!!.path!!, comicsData.thumbnail!!.extension!!)
    }

    fun setStoriesToView(storiesResults: StoriesResults) {
        if (storiesResults.description.toString() != "" && storiesResults.description != null) {
            binding.textDescription.text = storiesResults.description
        } else {
            binding.textDescription.text = resources.getString(R.string.desc)
        }

        binding.textNameTitle.text = storiesResults.title
        binding.textNameSubtitle.text = storiesResults.id.toString()
        binding.textImg1.text = storiesResults.characters!!.available.toString()
        binding.textImg2.text = storiesResults.comics!!.available.toString()
        binding.textImg3.text = storiesResults.events!!.available.toString()
        binding.textImg4.text = storiesResults.series!!.available.toString()

        binding.abilitiesText1.text = "Characters"
        binding.abilitiesText2.text = "Comics"
        binding.abilitiesText4.text = "Series"

        if (storiesResults.thumbnail != null) {
            setImage(
                binding.pageBackgroundImage,
                storiesResults.thumbnail!!.path!!,
                storiesResults.thumbnail!!.extension!!
            )
        } else {
            setImage(binding.pageBackgroundImage, "", "")
        }
    }

    fun setSeriesToView(seriesResults: SeriesResults) {
        if (seriesResults.description.toString() != "" && seriesResults.description != null) {
            binding.textDescription.text = seriesResults.description
        } else {
            binding.textDescription.text = resources.getString(R.string.desc)
        }

        binding.textNameTitle.text = seriesResults.title
        binding.textNameSubtitle.text = seriesResults.id.toString()
        binding.textImg1.text = seriesResults.characters!!.available.toString()
        binding.textImg3.text = seriesResults.events!!.available.toString()
        binding.textImg2.text = seriesResults.creators!!.available.toString()
        binding.textImg4.text = seriesResults.stories!!.available.toString()

        binding.abilitiesText1.text = "Characters"
        binding.abilitiesText2.text = "Comics"
        setImage(
            binding.pageBackgroundImage,
            seriesResults.thumbnail!!.path!!,
            seriesResults.thumbnail!!.extension!!
        )
    }

    fun setEventToView(eventsResults: EventsResults) {
        if (eventsResults.description.toString() != "") {
            binding.textDescription.text = eventsResults.description
        } else {
            binding.textDescription.text = resources.getString(R.string.desc)
        }
        binding.textNameTitle.text = eventsResults.title
        binding.textNameSubtitle.text = eventsResults.id.toString()
        binding.textImg1.text = eventsResults.characters!!.available.toString()
        binding.textImg3.text = eventsResults.series!!.available.toString()
        binding.textImg2.text = eventsResults.creators!!.available.toString()
        binding.textImg4.text = eventsResults.stories!!.available.toString()

        binding.abilitiesText1.text = "Characters"
        binding.abilitiesText2.text = "Creators"
        binding.abilitiesText3.text = "Comics"
        setImage(
            binding.pageBackgroundImage,
            eventsResults.thumbnail!!.path!!,
            eventsResults.thumbnail!!.extension!!
        )
    }

    fun setCreatorToView(creatorResults: CreatorResults) {
        binding.textNameTitle.text = creatorResults.fullName
        binding.textNameSubtitle.text = creatorResults.id.toString()
        binding.textImg1.text = creatorResults.comics!!.available.toString()
        binding.textImg3.text = creatorResults.events!!.available.toString()
        binding.textImg2.text = creatorResults.series!!.available.toString()
        binding.textImg4.text = creatorResults.stories!!.available.toString()
        setImage(
            binding.pageBackgroundImage,
            creatorResults.thumbnail!!.path!!,
            creatorResults.thumbnail!!.extension!!
        )
    }

    fun setCharactersToView(characterData: CharactersResults) {
        if (characterData.description.toString() != "") {
            binding.textDescription.text = characterData.description
        } else {
            binding.textDescription.text = resources.getString(R.string.desc)
        }
        binding.textNameTitle.text = characterData.name
        binding.textNameSubtitle.text = characterData.id.toString()
        binding.textImg1.text = characterData.comics!!.available.toString()
        binding.textImg3.text = characterData.events!!.available.toString()
        binding.textImg2.text = characterData.series!!.available.toString()
        binding.textImg4.text = characterData.stories!!.available.toString()
        setImage(
            binding.pageBackgroundImage,
            characterData.thumbnail!!.path!!,
            characterData.thumbnail!!.extension!!
        )
    }

    fun setClickListeners(adapterList: ArrayList<Any>, stringList: ArrayList<String>) {

        binding.img1.setOnClickListener {
            if (binding.textImg1.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[0] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[0]
                scrollToRv()
            }
        }
        binding.img2.setOnClickListener {
            if (binding.textImg2.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[1] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[1]
                scrollToRv()
            }
        }

        binding.img3.setOnClickListener {
            if (binding.textImg3.text != "0") {
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapterList[2] as RecyclerView.Adapter<*>
                binding.rvTitle.text = stringList[2]
                scrollToRv()
            }
        }

        binding.img4.setOnClickListener {
            if (binding.textImg4.text != "0") {
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


        setLocationRvCountText(firstBarValue?:0 ,binding.abilitiesRv1UpperText)
        setLocationRvCountText(thirdValue?:0 ,binding.abilitiesRv2UpperText)
        setLocationRvCountText(secondBarValue?:0 ,binding.abilitiesRv3UpperText)
        setLocationRvCountText(fourthBarValue?:0 ,binding.abilitiesRv4UpperText)

        binding.abilitiesRv1.adapter = CustomAttributeBarAdapter(firstBarValue ?: 0)
        binding.abilitiesRv2.adapter = CustomAttributeBarAdapter(thirdValue ?: 0)
        binding.abilitiesRv3.adapter = CustomAttributeBarAdapter(secondBarValue ?: 0)
        binding.abilitiesRv4.adapter = CustomAttributeBarAdapter(fourthBarValue ?: 0)
    }

    fun setLocationRvCountText( value: Int, view:TextView){
        if(value ==0){
            return
        }
        val coeff = resources.displayMetrics.density

        var y = 150/44
        var z =0
        value?.let {
            z = it/y
        }
        if(value!! >150){
            z=43
        }

        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        val leftMargin = (((z*5)+20)*coeff+0.5f).toInt()
        layoutParams.marginStart = leftMargin

        view.layoutParams=layoutParams
        view.text=value.toString()
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