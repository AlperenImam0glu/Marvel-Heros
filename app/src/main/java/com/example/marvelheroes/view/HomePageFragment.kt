package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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