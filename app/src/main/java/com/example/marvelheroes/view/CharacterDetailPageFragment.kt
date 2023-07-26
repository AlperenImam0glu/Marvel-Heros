package com.example.marvelheroes.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.adapter.CustomAttributeBarAdapter
import com.example.marvelheroes.databinding.FragmentCharacterDetailPageBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Math.max

@AndroidEntryPoint
class CharacterDetailPageFragment : Fragment() {


    private lateinit var result: Results
    lateinit var binding: FragmentCharacterDetailPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailPageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        arguments?.let {
            result = CharacterDetailPageFragmentArgs.fromBundle(it).results
        }


        setToolbarPosition()
        putDataToView()
        configureRecyclerView()

        binding.toolbarBackBtn.setOnClickListener {
            findNavController(this).popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun getMaxValueData(): Int {

        val comicsCount = result.comics!!.available ?: 0
        val eventCount = result.events!!.available ?: 0
        val seriesCount = result.series!!.available ?: 0
        val storiesCount = result.stories!!.available ?: 0
        return max(max(comicsCount, eventCount), max(seriesCount, storiesCount))

    }

    fun configureRecyclerView() {

        val maxValue = getMaxValueData()

        //burada factory kullanabilirim
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager2 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager3 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val layoutManager4 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        var adapter = CustomAttributeBarAdapter(maxValue, result.comics!!.available ?: 0)
        binding.recyclerviewComics.layoutManager = layoutManager
        binding.recyclerviewComics.adapter = adapter


        var adapter2 = CustomAttributeBarAdapter(maxValue, result.series!!.available ?: 0)
        binding.recyclerviewSeries.layoutManager = layoutManager2
        binding.recyclerviewSeries.adapter = adapter2

        var adapter3 = CustomAttributeBarAdapter(maxValue, result.events!!.available ?: 0)
        binding.recyclerviewEvents.layoutManager = layoutManager3
        binding.recyclerviewEvents.adapter = adapter3

        var adapter4 = CustomAttributeBarAdapter(maxValue, result.stories!!.available ?: 0)
        binding.recyclerviewStories.layoutManager = layoutManager4
        binding.recyclerviewStories.adapter = adapter4



    }

    fun setToolbarPosition() {
        //status barın yüksekliğini alıp toolbara ekliyoruz. Yoksa toolbar ekranın en tepesinden başlıyor
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        val param = binding.toolbar.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0, statusBarHeight, 0, 0)
        binding.toolbar.layoutParams = param
    }

    fun putDataToView() {

        if (result.description.toString() != "") {
            binding.textView7.text = result.description
        } else {
            binding.textView7.text = "Description not found\n\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam suscipit neque nulla," +
                    " bibendum malesuada nisi sagittis at. Sed venenatis accumsan risus eu tempor. Fusce " +
                    "aliquam dapibus turpis, id ultricies nulla laoreet nec. Suspendisse mattis lectus sit amet \n\n" +
                    "ipsum fermentum elementum. Nunc aliquam justo tincidunt lectus lacinia pulvinar. Pellentesque " +
                    "habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla efficitur,"
        }

        binding.textNameTitle.text = result.name
        binding.textNameSubtitle.text = result.id.toString()
        binding.textComicsCount.text = result.comics!!.available.toString()
        binding.textEventsCount.text = result.events!!.available.toString()
        binding.textSeriesCount.text = result.series!!.available.toString()
        binding.textStoriesCount.text = result.stories!!.available.toString()
        setImage(binding.image, result)
    }

    fun setImage(view: ImageView, data: Results) {

        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        var containsString = false

        url?.let {
            containsString = url!!.contains("image_not_available")
        }

        if (containsString) {
            view.setBackgroundResource(R.drawable.image_not_available_detail)
        } else {
            Glide.with(requireContext())
                .load(url)
                .centerCrop()
                .into(view);
        }
    }


}