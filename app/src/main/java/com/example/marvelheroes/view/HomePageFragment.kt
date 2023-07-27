package com.example.marvelheroes.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelheroes.R
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.adapter.itemAdapters.ButtonAdapter
import com.example.marvelheroes.adapter.itemAdapters.CharaterListAdapter
import com.example.marvelheroes.adapter.itemAdapters.ComicsListAdapter
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
    private lateinit var characterListAdapter:CharaterListAdapter
    private  var concatAdapter = ConcatAdapter()
    private lateinit var comicsListAdapter : ComicsListAdapter

    fun initViewModel() {
        lifecycleScope.launch {
            viewModelPaging.homePage.collectLatest {
                characterListAdapter.characterPagingAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModelPaging.comicsData.collectLatest {
                comicsListAdapter.comicsPagingAdapter.submitData(it)
            }
        }
    }

    fun initViewMode2() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        comicsListAdapter = ComicsListAdapter(requireContext())
        characterListAdapter = CharaterListAdapter(requireContext())
        initViewModel()
        initViewMode2()

        concatAdapter = ConcatAdapter(headerAdapter,buttonAdapter,characterListAdapter,comicsListAdapter)
        binding.homepageRv.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = concatAdapter
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }

    fun changeStatusBarColor(color:Int){
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }





}