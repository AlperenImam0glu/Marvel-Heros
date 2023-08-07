package com.example.marvelheroes.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
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
    private lateinit var characterPagingAdapter: CharacterPagingAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: SeeAllPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.let {
            WindowCompat.getInsetsController(it.window, it.window.decorView).apply {
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                show(WindowInsetsCompat.Type.statusBars())
            }

            it.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        sharedViewModel.setCurrentPage(Enums.SeeAll)
        binding = FragmentSeeAllPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterPagingAdapter = CharacterPagingAdapter(requireContext(), sharedViewModel)
        getData()

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = characterPagingAdapter

        binding.toolbarBackBtn.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    fun getData() {
        lifecycleScope.launch {
            viewModel.getAllCharacters.collectLatest {
                characterPagingAdapter.submitData(it)
            }
        }
    }
}