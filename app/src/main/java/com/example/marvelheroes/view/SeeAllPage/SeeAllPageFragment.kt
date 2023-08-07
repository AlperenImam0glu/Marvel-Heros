package com.example.marvelheroes.view.SeeAllPage

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.ComicsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.CreatorsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.SeriesPagingAdapter
import com.example.marvelheroes.adapter.pagingAdapters.StoriesPagingAdapter
import com.example.marvelheroes.databinding.FragmentSeeAllPageBinding
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.viewmodel.SeeAllPageViewModel
import com.example.marvelheroes.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeeAllPageFragment : Fragment() {


    private lateinit var binding: FragmentSeeAllPageBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: SeeAllPageViewModel by viewModels()
    private lateinit var initViewModelForSeeAllPage: InitViewModelForSeeAllPage
    private lateinit var comicsPagingAdapter: ComicsPagingAdapter
    private lateinit var seriesPagingAdapter: SeriesPagingAdapter
    private lateinit var eventsPagingAdapter: EventsPagingAdapter
    private lateinit var storiesPagingAdapter: StoriesPagingAdapter
    private lateinit var characterPagingAdapter: CharacterPagingAdapter
    private lateinit var creatorPagingsAdapter: CreatorsPagingAdapter
    private lateinit var type: Enums

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
        binding = FragmentSeeAllPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmer.startShimmer()

        arguments?.let {
            type = SeeAllPageFragmentArgs.fromBundle(it).dataType
        }
        type
        characterPagingAdapter = CharacterPagingAdapter(requireContext(), sharedViewModel)
        comicsPagingAdapter = ComicsPagingAdapter(requireContext(), sharedViewModel)
        seriesPagingAdapter = SeriesPagingAdapter(requireContext(), sharedViewModel)
        creatorPagingsAdapter = CreatorsPagingAdapter(requireContext(), sharedViewModel)
        storiesPagingAdapter = StoriesPagingAdapter(requireContext(), sharedViewModel)
        eventsPagingAdapter = EventsPagingAdapter(requireContext(), sharedViewModel)

        initViewModelForSeeAllPage = InitViewModelForSeeAllPage(
            viewModel,
            lifecycle,
            characterPagingAdapter,
            comicsPagingAdapter,
            creatorPagingsAdapter,
            eventsPagingAdapter,
            seriesPagingAdapter,
            storiesPagingAdapter,
            binding
        )
        observer()
        createViewByType()

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.toolbarBackBtn.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    fun createViewByType() {

        when (type) {
            Enums.Character -> {
                binding.recyclerView.adapter = characterPagingAdapter
                initViewModelForSeeAllPage.getAllCharacters()
                binding.toolbarText.text ="All Characters"
            }

            Enums.Comic -> {
                binding.recyclerView.adapter = comicsPagingAdapter
                initViewModelForSeeAllPage.getAllComics()
                binding.toolbarText.text ="All Comics"
            }

            Enums.Creator -> {
                binding.recyclerView.adapter = creatorPagingsAdapter
                initViewModelForSeeAllPage.getAllCreators()
                binding.toolbarText.text ="All Creators"
            }

            Enums.Series -> {
                binding.recyclerView.adapter = seriesPagingAdapter
                initViewModelForSeeAllPage.getAllSeries()
                binding.toolbarText.text ="All Series"
            }

            Enums.Story -> {
                binding.recyclerView.adapter = storiesPagingAdapter
                initViewModelForSeeAllPage.getAllStories()
                binding.toolbarText.text ="All Stories"
            }

            Enums.Event -> {
                binding.recyclerView.adapter = eventsPagingAdapter
                initViewModelForSeeAllPage.getAllEvent()
                binding.toolbarText.text ="All Events"
            }

            else -> {

            }
        }

    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.setCurrentPage(Enums.SeeAll)
    }

    fun observer() {
        viewModel.dataLoadingState.observe(viewLifecycleOwner) {
            if (!it) {
                binding.shimmer.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }
    }
}