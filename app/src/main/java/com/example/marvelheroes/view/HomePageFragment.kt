package com.example.marvelheroes.view

import android.graphics.Color
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelheroes.R
import com.example.marvelheroes.adapter.ComicsAdapter
import com.example.marvelheroes.adapter.CharacterPagingAdapter
import com.example.marvelheroes.adapter.itemAdapters.ButtonAdapter
import com.example.marvelheroes.adapter.itemAdapters.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdapters.HeaderAdapter
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private val viewModelPaging: HomeViewModel by viewModels()
    private var headerAdapter :HeaderAdapter = HeaderAdapter()
    private var buttonAdapter :ButtonAdapter = ButtonAdapter()
    private lateinit var listAdapter:CharaterListAdapter
    private  var concatAdapter = ConcatAdapter()


    private lateinit var recyclerViewAdapter: CharacterPagingAdapter


    fun initViewModel() {
        lifecycleScope.launch {
            viewModelPaging.homePage.collectLatest {
                listAdapter.CharacterPagingAdapter.submitData(it)
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)
        listAdapter = CharaterListAdapter(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initViewModel()
        concatAdapter = ConcatAdapter(headerAdapter,buttonAdapter,listAdapter)
        binding.homepageRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = concatAdapter
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





}