package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelheroes.adapter.itemAdapters.ButtonAdapter
import com.example.marvelheroes.adapter.itemAdapters.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdapters.ComicsListAdapter
import com.example.marvelheroes.adapter.itemAdapters.CreatorListAdapter
import com.example.marvelheroes.adapter.itemAdapters.EventListAdapter
import com.example.marvelheroes.adapter.itemAdapters.HeaderAdapter
import com.example.marvelheroes.adapter.itemAdapters.SeriesListAdapter
import com.example.marvelheroes.adapter.itemAdapters.StoriesListAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomeViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val viewModelPaging: HomeViewModel by viewModels()
    private var concatAdapter = ConcatAdapter()
    private var headerAdapter: HeaderAdapter = HeaderAdapter()
    private var buttonAdapter: ButtonAdapter = ButtonAdapter()
    private lateinit var characterListAdapter: CharaterListAdapter
    private lateinit var comicsListAdapter: ComicsListAdapter
    private lateinit var creatorListAdapter: CreatorListAdapter
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var seriesListAdapter: SeriesListAdapter
    private lateinit var storiesListAdapter: StoriesListAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        comicsListAdapter = ComicsListAdapter(requireContext(),"Comics",sharedViewModel)
        characterListAdapter = CharaterListAdapter(requireContext(),"Heroes",sharedViewModel)
        creatorListAdapter = CreatorListAdapter(requireContext(),"Creators",sharedViewModel)
        eventListAdapter = EventListAdapter(requireContext(),"Events",sharedViewModel)
        seriesListAdapter = SeriesListAdapter(requireContext(),"Series",sharedViewModel)
        storiesListAdapter = StoriesListAdapter(requireContext(),"Stories",sharedViewModel)

        initViewModel()

        concatAdapter = ConcatAdapter(
            headerAdapter,
            buttonAdapter,
            characterListAdapter,
            comicsListAdapter,
            creatorListAdapter,
            eventListAdapter,
            seriesListAdapter,
            storiesListAdapter
        )

        binding.homepageRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = concatAdapter
        }
    }

    private fun initViewModel() {
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