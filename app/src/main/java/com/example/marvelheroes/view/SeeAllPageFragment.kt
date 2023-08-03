package com.example.marvelheroes.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.marvelheroes.R
import com.example.marvelheroes.databinding.FragmentSeeAllPageBinding

class SeeAllPageFragment : Fragment() {


    private lateinit var binding : FragmentSeeAllPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSeeAllPageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarBackBtn.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }
}