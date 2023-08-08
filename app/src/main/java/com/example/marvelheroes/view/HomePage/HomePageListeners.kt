package com.example.marvelheroes.view.HomePage

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.databinding.FragmentHomePageBinding
import com.example.marvelheroes.safeNavigate
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.viewmodel.HomePageViewModel

class HomePageListeners(
    val binding: FragmentHomePageBinding,
    val homePageViewModel: HomePageViewModel
) {
    private val destinationPage = Enums.HomeToSeeAll

    fun setListeners() {

        binding.buttons.heroButton.setOnClickListener {
            Navigation.findNavController(it).safeNavigate(destinationPage, Enums.Character)
        }

        binding.buttons.villianButton.setOnClickListener {
            Navigation.findNavController(it).safeNavigate(destinationPage, Enums.Comic)
        }

        binding.buttons.antiHeroButton.setOnClickListener {
            Navigation.findNavController(it).safeNavigate(destinationPage, Enums.Creator)
        }

        binding.buttons.alienButton.setOnClickListener {
            Navigation.findNavController(it).safeNavigate(destinationPage, Enums.Series)
        }

        binding.buttons.humanButton.setOnClickListener {
            Navigation.findNavController(it).safeNavigate(destinationPage, Enums.Story)
        }

        binding.homepageRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        closeViewWithAnimation(binding.headerLayout)
                    }
                }
            }
        })
    }

    private fun closeViewWithAnimation(view: View) {
        val height = view.height
        val valueAnimator = ValueAnimator.ofInt(height, 0)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            view.layoutParams = layoutParams
            if (value == 0) {
                view.visibility = View.GONE
            }
        }
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.duration = 500
        valueAnimator.start()
        homePageViewModel.isHeadTextOpen.value = false
    }
}