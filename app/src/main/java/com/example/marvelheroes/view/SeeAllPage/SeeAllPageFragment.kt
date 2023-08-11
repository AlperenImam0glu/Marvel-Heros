package com.example.marvelheroes.view.SeeAllPage

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
    val handler = Handler(Looper.getMainLooper())
    val delayMillis = 500
    private var runnable: Runnable? = null
    private var isInput = false
    private var isInputChange = false
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
        if (!isInput) {
            createViewByType()
        }


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        val imm =
                            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
                        binding.editTextSearch.clearFocus()
                    }
                }
            }


        })



        binding.editTextSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var input = ""
                s?.let {
                    input = s.trim().toString()
                    isInputChange = true
                }

                if (input == "" && isInput) {
                    isInput = false
                    Log.e("cizim", "arama çalıştı sıfırlandı")
                    binding.recyclerView.smoothScrollToPosition(0)
                    viewModel.name.value = input
                    createViewByType()
                } else if (input != "" && isInputChange) {
                    isInput = true
                    isInputChange = false
                    Log.e("cizim", "arama çalıştı arandı")
                    binding.recyclerView.smoothScrollToPosition(0)
                    viewModel.name.value = input
                    runnable?.let { handler.removeCallbacks(it) }
                    runnable = Runnable {
                        searchWithDataType()
                    }
                    handler.postDelayed(runnable!!, delayMillis.toLong())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.toolbarBackBtn.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    fun createViewByType() {
        Log.e("cizim", "ekran türe göre çizildi")

        when (type) {
            Enums.Character -> {
                binding.recyclerView.adapter = characterPagingAdapter
                initViewModelForSeeAllPage.getAllCharacters()
                binding.toolbarText.text = "All Characters"
            }

            Enums.Comic -> {
                binding.recyclerView.adapter = comicsPagingAdapter
                initViewModelForSeeAllPage.getAllComics()
                binding.toolbarText.text = "All Comics"
            }

            Enums.Creator -> {
                binding.recyclerView.adapter = creatorPagingsAdapter
                initViewModelForSeeAllPage.getAllCreators()
                binding.toolbarText.text = "All Creators"
            }

            Enums.Series -> {
                binding.recyclerView.adapter = seriesPagingAdapter
                initViewModelForSeeAllPage.getAllSeries()
                binding.toolbarText.text = "All Series"
            }

            Enums.Story -> {
                binding.editTextSearch.hint = "Search is not available for stories"
                binding.editTextSearch.isEnabled = false
                binding.editTextSearch.isFocusable = false
                binding.editTextSearch.isFocusableInTouchMode = false
                binding.recyclerView.adapter = storiesPagingAdapter
                initViewModelForSeeAllPage.getAllStories()
                binding.toolbarText.text = "All Stories"
            }

            Enums.Event -> {
                binding.recyclerView.adapter = eventsPagingAdapter
                initViewModelForSeeAllPage.getAllEvent()
                binding.toolbarText.text = "All Events"
            }

            else -> {
                Log.e("error", "Incorrect Enum usage")
            }
        }
    }

    fun searchWithDataType() {

        when (type) {
            Enums.Character -> {
                binding.recyclerView.adapter = characterPagingAdapter
                initViewModelForSeeAllPage.searchCharacter()
            }

            Enums.Comic -> {
                binding.recyclerView.adapter = comicsPagingAdapter
                initViewModelForSeeAllPage.searchComics()
            }

            Enums.Creator -> {
                binding.recyclerView.adapter = creatorPagingsAdapter
                initViewModelForSeeAllPage.searchCreators()
            }

            Enums.Series -> {
                binding.recyclerView.adapter = seriesPagingAdapter
                initViewModelForSeeAllPage.searchSeries()
            }

            Enums.Event -> {
                binding.recyclerView.adapter = eventsPagingAdapter
                initViewModelForSeeAllPage.searchEvents()
            }

            else -> {
                Log.e("error", "Incorrect Enum usage")
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