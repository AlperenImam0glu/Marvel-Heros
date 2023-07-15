package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.marvelheroes.R
import com.example.marvelheroes.adapter.CardItemAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomePageViewModel

class HomePageFragment : Fragment() {

    private lateinit var viewModel: HomePageViewModel
    private lateinit var binding : FragmentHomePageBinding
    private lateinit var  adapter : CardItemAdapter
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
        adapter = CardItemAdapter(arrayListOf(), requireContext() )
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        viewModel.refreshData()

        binding.characterRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.characterRecyclerView.adapter = adapter
        observeLiveData()

        super.onViewCreated(view, savedInstanceState)
    }

    fun observeLiveData() {
        viewModel.characterList.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
               adapter.updateCountryList(countries.results)
            }
        })


    }

}