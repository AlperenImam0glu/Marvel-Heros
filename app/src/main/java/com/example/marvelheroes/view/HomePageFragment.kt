package com.example.marvelheroes.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.marvelheroes.R
import com.example.marvelheroes.adapter.ComicsAdapter
import com.example.marvelheroes.adapter.CharacterPagingAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomePageViewModel
import com.example.marvelheroes.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomePageFragment : Fragment() {

    private lateinit var viewModel: HomePageViewModel
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var recyclerViewAdapter: CharacterPagingAdapter
    private lateinit var comicsAdapter: ComicsAdapter
    private val viewModelPaging: HomeViewModel by viewModels()

    fun initViewModel() {
        lifecycleScope.launch {
            viewModelPaging.homePage.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }

    }

    fun initRecyclerView() {
        lifecycleScope.launch {
            binding.characterRecyclerView.adapter = recyclerViewAdapter
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        recyclerViewAdapter = CharacterPagingAdapter(requireContext())
        initViewModel()
        initRecyclerView()
        comicsAdapter= ComicsAdapter(arrayListOf(), requireContext())
        binding.comicsRecyclerView.adapter = comicsAdapter

        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.primary_silver)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]
        viewModel.refreshData()

        observeLiveData()
        binding.humanButton.setOnClickListener {

        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {

        super.onPause()
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

    }
    private fun observeLiveData() {
        /*
        viewModel.characterList.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                binding.characterAdapter!!.updateCharacterList(data.results)
            }
        })*/

        viewModel.comicsList.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                comicsAdapter.updateCharacterList(data.results)
            }
        })
    }

    fun scrollToCharacter() {
        viewModel.scrollToCharacter(binding)
    }

    fun scrollToComics() {
        viewModel.scrollToComics(binding)
    }


}