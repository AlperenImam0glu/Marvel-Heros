package com.example.marvelheroes.view

import android.os.Bundle
import android.util.Log
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
import com.example.marvelheroes.Results
import com.example.marvelheroes.adapter.CustomAttributeBarAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.ComicsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.SeriesPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.StoriesPagingAdapter
import com.example.marvelheroes.databinding.FragmentCharacterDetailPageBinding
import com.example.marvelheroes.models.detail_page.DetailPageModel
import com.example.marvelheroes.viewmodel.DetailPageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailPageFragment : Fragment() {

    private lateinit var charactersData: Results
    private lateinit var comicsData: ComicsResults

    lateinit var binding: FragmentCharacterDetailPageBinding
    private val viewModelPaging: DetailPageViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var comicsAdapter: ComicsPagingAdapter
    lateinit var seriesAdapter: SeriesPagingAdapter
    lateinit var eventsAdapter: EventsPagingAdapter
    lateinit var storiesAdapter: StoriesPagingAdapter
    lateinit var charactersAdapter: CharacterPagingAdapter
    var type: Int = 0


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
        eventsAdapter = EventsPagingAdapter(requireContext())
        storiesAdapter = StoriesPagingAdapter(requireContext())
        charactersAdapter = CharacterPagingAdapter(requireContext(), sharedViewModel)

        arguments?.let {
            type = CharacterDetailPageFragmentArgs.fromBundle(it).type
        }
        getData(type)
        initViewModel()
        setToolbarPosition()
        // setDataToView()
        // setClickListeners()

        binding.toolbarBackBtn.setOnClickListener {

            findNavController(this).popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(type==0){
            var tmp = sharedViewModel.getCharacter()
            tmp?.let {
                if (tmp.isNotEmpty()) {
                    Log.e("liste", "silinmeden önce ${tmp.size}")
                    tmp.removeLast()
                    sharedViewModel.setCharacter(tmp)
                    Log.e("liste", "silinmeden sonra ${tmp.size}")
                }
            }
        }
    }

    fun getData(type: Int) {
        if (type == 0) {
                        sharedViewModel.getCharacter()?.let {
                            charactersData = it.last()
                            viewModelPaging.id = charactersData.id.toString()
                            setCharactersToView(charactersData)
                            Log.e("ch","$it")
                            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            binding.rv.layoutManager = layoutManager
                            binding.rv.adapter = comicsAdapter
                        }

        } else {
            sharedViewModel.getComics()?.let {
                comicsData = it
                viewModelPaging.id = comicsData.id.toString()
                setComicsToView(comicsData)
                val layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                binding.rv.layoutManager = layoutManager
                binding.rv.adapter = charactersAdapter
                Log.e("ch", "$it")
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
        setImage(binding.image, comicsData.thumbnail!!.path!!, comicsData.thumbnail!!.extension!!)

        configureRecyclerViewComics(comicsData)
        setClickListenersComics()
    }

    fun setCharactersToView(characterData: Results) {

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
        setClickListeners()

    }


    fun initViewModel() {
        lifecycleScope.launch {
            viewModelPaging.comicsData.collectLatest {
                comicsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.seriesData.collectLatest {
                seriesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.eventsData.collectLatest {
                eventsAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.storiesData.collectLatest {
                storiesAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.charactersData.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
    }

    fun setClickListenersComics() {

        binding.img1.setOnClickListener {
            binding.rv.adapter = charactersAdapter
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


    fun setClickListeners() {

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

    fun configureRecyclerViewCharacters(characterData: Results) {

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

        Log.e("detail", "$comicsData")

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

    fun setDataToView() {


        // setImage(binding.image, pageModel.thumbnail!!)
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