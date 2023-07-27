package com.example.marvelheroes

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageFromInternet(url:String,view: ImageView) {
    var isImageEmpty = false
    var imageUrl=""
    url?.let {
        isImageEmpty = url!!.contains("image_not_available")
    }
    if(isImageEmpty){
        imageUrl = "https://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available/portrait_xlarge.jpg"
    }else{
        imageUrl = url
    }
        Glide.with(context).load(imageUrl).centerCrop().into(view)
}







