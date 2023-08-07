package com.example.marvelheroes

import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.view.DetailPage.DetailPageFragmentDirections
import com.example.marvelheroes.view.HomePage.HomePageFragmentDirections
import com.example.marvelheroes.view.SeeAllPage.SeeAllPageFragmentDirections

fun ImageView.loadImageFromInternet(url: String, view: ImageView) {
    var isImageEmpty = false
    var imageUrl = ""
    url?.let {
        isImageEmpty = url!!.contains("image_not_available")
    }
    if (url.length < 10) {
        isImageEmpty = true
    }
    if (isImageEmpty) {
        imageUrl =
            "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg"
    } else {
        imageUrl = url
    }
    Glide.with(context).load(imageUrl).centerCrop().into(view)
}


fun NavController.safeNavigate(currentPage: Enums, dataType: Enums) {

    when (currentPage) {
        Enums.Home -> {
            val action =
                HomePageFragmentDirections.actionHomePageFragmentToDetailPageFragment(dataType)
            navigate(action)

        }

        Enums.Detail -> {
            val action =
                DetailPageFragmentDirections.detailPageFragmentToDetailPageFragment(dataType)
            navigate(action)

        }

        Enums.SeeAll -> {
            val action =
                SeeAllPageFragmentDirections.actionSeeAllPageFragmentToDetailPageFragment(dataType)
            navigate(action)
        }

        else -> {
            Toast.makeText(context, "Navigasyon in development stage", Toast.LENGTH_SHORT).show()
        }
    }

}








