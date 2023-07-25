package com.example.marvelheroes

import android.app.Activity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.downloadFromUrl(url: String?) {
    val options = RequestOptions()
        .placeholder(R.mipmap.ic_launcher_round)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}







