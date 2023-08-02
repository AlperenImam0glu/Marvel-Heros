package com.example.marvelheroes.view

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.adapter.itemAdapters.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdapters.ComicsListAdapter
import com.example.marvelheroes.adapter.itemAdapters.CreatorListAdapter
import com.example.marvelheroes.adapter.itemAdapters.EventListAdapter
import com.example.marvelheroes.adapter.itemAdapters.SeriesListAdapter
import com.example.marvelheroes.adapter.itemAdapters.StoriesListAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.view.HomePage.InitViewModelForHomePage
import com.example.marvelheroes.viewmodel.HomePageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val viewModelPaging: HomePageViewModel by viewModels()
    private var concatAdapter = ConcatAdapter()
    private lateinit var characterListAdapter: CharaterListAdapter
    private lateinit var comicsListAdapter: ComicsListAdapter
    private lateinit var creatorListAdapter: CreatorListAdapter
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var seriesListAdapter: SeriesListAdapter
    private lateinit var storiesListAdapter: StoriesListAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var inıtViewModelForHomePage: InitViewModelForHomePage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (viewModelPaging.isOpen.value == false) {
            binding.headerLayout.visibility = View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shimmer.startShimmer()

        comicsListAdapter = ComicsListAdapter(requireContext(), "Comics", sharedViewModel)
        characterListAdapter = CharaterListAdapter(requireContext(), "Heroes", sharedViewModel)
        creatorListAdapter = CreatorListAdapter(requireContext(), "Creators", sharedViewModel)
        eventListAdapter = EventListAdapter(requireContext(), "Events", sharedViewModel)
        seriesListAdapter = SeriesListAdapter(requireContext(), "Series", sharedViewModel)
        storiesListAdapter = StoriesListAdapter(requireContext(), "Stories", sharedViewModel)

        inıtViewModelForHomePage = InitViewModelForHomePage(
            viewModelPaging,
            lifecycle,
            characterListAdapter,
            comicsListAdapter,
            creatorListAdapter,
            eventListAdapter,
            seriesListAdapter,
            storiesListAdapter,
            binding
        )

        inıtViewModelForHomePage.initViewModel()

        concatAdapter = ConcatAdapter(
            characterListAdapter,
            comicsListAdapter,
            creatorListAdapter,
            eventListAdapter,
            seriesListAdapter,
            storiesListAdapter
        )

        binding.homepageRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        closeViewWithAnimation(binding.headerLayout)
                    }
                }
            }
        })

        binding.homepageRv.adapter = concatAdapter
    }

    private fun closeViewWithAnimation(view: View) {
        val initialHeight = view.height
        val valueAnimator = ValueAnimator.ofInt(initialHeight, 0)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams = layoutParams
            if (value == 0) {
                view.visibility = View.GONE
            }
        }
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.duration = 500
        valueAnimator.start()
        viewModelPaging.isOpen.value = false
    }



}