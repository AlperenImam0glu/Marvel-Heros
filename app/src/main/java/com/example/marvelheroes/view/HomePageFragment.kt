package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelheroes.adapter.CharacterAdapter
import com.example.marvelheroes.adapter.ComicsAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomePageViewModel

class HomePageFragment : Fragment() {

    private lateinit var viewModel: HomePageViewModel
    private lateinit var binding : FragmentHomePageBinding
    private lateinit var characterAdapter : CharacterAdapter
    private lateinit var comicsAdapter : ComicsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        characterAdapter = CharacterAdapter(arrayListOf(), requireContext() )
        comicsAdapter = ComicsAdapter(arrayListOf(), requireContext() )

        viewModel = ViewModelProvider(this)[HomePageViewModel::class.java]
        viewModel.refreshData()

        binding.characterRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.characterRecyclerView.adapter = characterAdapter

        binding.rcComics.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.rcComics.adapter = comicsAdapter

        observeLiveData()

        super.onViewCreated(view, savedInstanceState)
    }

    fun observeLiveData() {
        viewModel.characterList.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
               characterAdapter.updateCharacterList(countries.results)
            }
        })

        viewModel.comicsList.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
               comicsAdapter.updateCharacterList(countries.results)
            }
        })


    }

}