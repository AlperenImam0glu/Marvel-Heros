package com.example.marvelheroes

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageFromInternet(url:String,view: ImageView) {
    var containsString = false
    url?.let {
        containsString = url!!.contains("image_not_available")
    }
    if (containsString) {
        view.setBackgroundResource(R.drawable.image_not_available)
    } else {
        Glide.with(context).load(url).centerCrop().into(view)

    }
}







