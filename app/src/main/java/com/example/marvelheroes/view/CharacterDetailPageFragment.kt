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
import com.example.marvelheroes.maxOfFourNumber
import java.lang.Math.max


class CharacterDetailPageFragment : Fragment() {

    lateinit  var adapter : CustomAttributeBarAdapter

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

    private lateinit var result: Results
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            result = CharacterDetailPageFragmentArgs.fromBundle(it).results
        }

        val maxValue = getMaxValueData()

        adapter = CustomAttributeBarAdapter(maxValue,result.comics!!.available ?:0)
        setToolbarPosition()
        putDataToView()
        configureRecyclerView()


        binding.iconBack.setOnClickListener {
            findNavController(this).popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun getMaxValueData(): Int {

        var comicsCount =result.comics!!.available ?: 0
        var eventCount =result.events!!.available ?: 0
        var seriesCount =result.series!!.available ?: 0
        var storiesCount =result.stories!!.available ?: 0
        return max(max(comicsCount,eventCount), max(seriesCount,storiesCount))

    }

    fun findMaxNumber(a: Int, b: Int, c: Int, d: Int): Int {
        val maxAB = if (a > b) a else b
        val maxCD = if (c > d) c else d
        return if (maxAB > maxCD) maxAB else maxCD
    }
    fun configureRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewComics.layoutManager = layoutManager
        binding.recyclerviewComics.adapter = adapter
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
            view.setBackgroundResource(R.drawable.spider_man)
        } else {
            Glide.with(requireContext())
                .load(url)
                .centerCrop()
                .into(view);
        }
    }


}