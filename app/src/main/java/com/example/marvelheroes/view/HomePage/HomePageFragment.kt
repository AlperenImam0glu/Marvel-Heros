package com.example.marvelheroes.view.HomePage

import android.animation.ValueAnimator
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.adapter.itemAdaptersForConcat.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.ComicsListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.CreatorListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.EventListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.SeriesListAdapter
import com.example.marvelheroes.adapter.itemAdaptersForConcat.StoriesListAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.view.HomePage.HomePageFragmentDirections
import com.example.marvelheroes.viewmodel.HomePageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.Exception

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val homePageViewModel: HomePageViewModel by viewModels()
    private var concatAdapter = ConcatAdapter()
    private lateinit var characterListAdapter: CharaterListAdapter
    private lateinit var comicsListAdapter: ComicsListAdapter
    private lateinit var creatorListAdapter: CreatorListAdapter
    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var seriesListAdapter: SeriesListAdapter
    private lateinit var storiesListAdapter: StoriesListAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var initViewModelForHomePage: InitViewModelForHomePage
    private lateinit var countDownTimer: CountDownTimer
    private var networkState: Boolean = false
    private lateinit var homePageListeners: HomePageListeners

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let {
            WindowCompat.getInsetsController(it.window, it.window.decorView).apply {
                systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                show(WindowInsetsCompat.Type.statusBars())
            }

            it.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (homePageViewModel.isHeadTextOpen.value == false) {
            binding.headerLayout.visibility = View.GONE
        }
        sharedViewModel.setCurrentPage(Enums.Home)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startConnectionChecker()
        networkState = isInternetAvailable(requireContext())

        binding.shimmer.startShimmer()

        comicsListAdapter = ComicsListAdapter(requireContext(), "Comics", sharedViewModel)
        characterListAdapter = CharaterListAdapter(requireContext(), "Characters", sharedViewModel)
        creatorListAdapter = CreatorListAdapter(requireContext(), "Creators", sharedViewModel)
        eventListAdapter = EventListAdapter(requireContext(), "Events", sharedViewModel)
        seriesListAdapter = SeriesListAdapter(requireContext(), "Series", sharedViewModel)
        storiesListAdapter = StoriesListAdapter(requireContext(), "Stories", sharedViewModel)

        initViewModelForHomePage = InitViewModelForHomePage(
            homePageViewModel,
            lifecycle,
            characterListAdapter,
            comicsListAdapter,
            creatorListAdapter,
            eventListAdapter,
            seriesListAdapter,
            storiesListAdapter,
            binding
        )
        homePageListeners = HomePageListeners(binding, homePageViewModel)
        homePageListeners.setListeners()

        initViewModelForHomePage.initViewModel()

        concatAdapter = ConcatAdapter()

        observer()

        binding.homepageRv.adapter = concatAdapter
    }

    private fun startConnectionChecker() {
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                networkState = isInternetAvailable(requireContext())
                if (!networkState && binding.homepageRv.visibility != View.VISIBLE) {
                    binding.shimmer.visibility = View.GONE
                    binding.networkText.visibility = View.VISIBLE
                } else if (binding.homepageRv.visibility != View.VISIBLE) {
                    networkState = false
                    binding.shimmer.visibility = View.GONE
                    binding.networkText.visibility = View.VISIBLE
                }
            }
        }
        countDownTimer.start()
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    fun showData() {
        binding.homepageRv.smoothScrollToPosition(0)
        binding.shimmer.visibility = View.GONE
        binding.homepageRv.visibility = View.VISIBLE
    }

    fun observer() {
        homePageViewModel.comicsLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                try {
                    concatAdapter.addAdapter(0, characterListAdapter)
                } catch (e: Exception) {
                    concatAdapter.addAdapter(characterListAdapter)
                }
                showData()
            }
        }
        homePageViewModel.comicsLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                try {
                    concatAdapter.addAdapter(1, comicsListAdapter)
                } catch (e: Exception) {
                    concatAdapter.addAdapter(comicsListAdapter)
                }
                showData()
            }
        }
        homePageViewModel.creatorsLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                try {
                    concatAdapter.addAdapter(2, creatorListAdapter)
                } catch (e: Exception) {
                    concatAdapter.addAdapter(creatorListAdapter)
                }
                showData()
            }
        }
        homePageViewModel.seriesLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                try {
                    concatAdapter.addAdapter(3, seriesListAdapter)
                } catch (e: Exception) {
                    concatAdapter.addAdapter(seriesListAdapter)
                }
                showData()
            }
        }
        homePageViewModel.eventsLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                try {
                    concatAdapter.addAdapter(4, eventListAdapter)
                } catch (e: Exception) {
                    concatAdapter.addAdapter(eventListAdapter)
                }
                showData()
            }
        }

        homePageViewModel.storiesLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                try {
                    concatAdapter.addAdapter(5, storiesListAdapter)
                } catch (e: Exception) {
                    concatAdapter.addAdapter(storiesListAdapter)
                }
                showData()
            }
        }
    }


}