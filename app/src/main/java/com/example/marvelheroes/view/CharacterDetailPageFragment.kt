package com.example.marvelheroes.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.bumptech.glide.Glide
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.FragmentCharacterDetailPageBinding


class CharacterDetailPageFragment : Fragment() {

        lateinit var binding : FragmentCharacterDetailPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailPageBinding.inflate(inflater,container,false)


        return binding.root
    }

    private lateinit var result : Results
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            result = CharacterDetailPageFragmentArgs.fromBundle(it).results
        }
        //var k = character.data!!.results.description.toString()
        if(result.description.toString() !=""){
            binding.textView7.text = result.description
        }else{
            binding.textView7.text = "Description not found"
        }

        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        val param = binding.toolbar.layoutParams as ViewGroup.MarginLayoutParams
        param.setMargins(0,statusBarHeight,0,0)
        binding.toolbar.layoutParams =param

        binding.textNameTitle.text = result.name
        binding.textNameSubtitle.text = result.id.toString()
        binding.textComicsCount.text = result.comics!!.available.toString()
        binding.textEventsCount.text = result.events!!.available.toString()
        binding.textSeriesCount.text = result.series!!.available.toString()
        binding.textStoriesCount.text = result.series!!.available.toString()

        setImage(binding.image,result)

        binding.iconBack.setOnClickListener {
            findNavController(this).popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun setImage(view: ImageView, data: Results){

        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        var containsString = false

        url?.let {
            containsString = url!!.contains("image_not_available")
        }

        if(containsString){
            view.setBackgroundResource(R.drawable.image_not_available)
        }else{
            Glide.with(requireContext())
                .load(url)
                .centerCrop()
                .into(view);
        }
    }


}