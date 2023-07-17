package com.example.marvelheroes.repo

import com.example.marvelheroes.databinding.FragmentHomePageBinding

class HomePageFragmentRepository {
     fun scrollToCharacter(binding: FragmentHomePageBinding){
        binding.scrollView.smoothScrollBy(0,binding.characterRecyclerView.top)
    }
     fun scrollToComics(binding: FragmentHomePageBinding){
        binding.scrollView.smoothScrollBy(0,binding.comicsRecyclerView.bottom)
    }
}