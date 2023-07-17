package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelheroes.R
import com.example.marvelheroes.adapter.CharacterAdapter
import com.example.marvelheroes.adapter.ComicsAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomePageViewModel

class HomePageFragment : Fragment() {

    private lateinit var viewModel: HomePageViewModel
    private lateinit var binding : FragmentHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page,container,false)
        binding.fragment = this
        binding.characterAdapter = CharacterAdapter(arrayListOf(), requireContext())
        binding.comicsAdapter= ComicsAdapter(arrayListOf(), requireContext() )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel= ViewModelProvider(this)[HomePageViewModel::class.java]
        viewModel.refreshData()

        observeLiveData()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeLiveData() {
        viewModel.characterList.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
               binding.characterAdapter!!.updateCharacterList(countries.results)
            }
        })

        viewModel.comicsList.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                binding.comicsAdapter!!.updateCharacterList(countries.results)
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