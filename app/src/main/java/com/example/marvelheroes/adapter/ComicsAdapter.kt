package com.example.marvelheroes.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.ComicsThumbnail
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.HomePageCardDesignBinding
import com.example.marvelheroes.loadImageFromInternet

class ComicsAdapter(val comicsList: ArrayList<ComicsResults>, val context: Context) :
    RecyclerView.Adapter<ComicsAdapter.CardViewHolder>() {

    class CardViewHolder(val binding: HomePageCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =  HomePageCardDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.cardSubtitle.text = comicsList[position].id.toString()
        holder.binding.cardTitle.text = comicsList[position].title
        setImage(holder.binding.imageView, comicsList[position].thumbnail!!)
    }

    fun updateCharacterList(newList: List<ComicsResults>) {
        comicsList.clear()
        comicsList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setImage(view: ImageView, data: ComicsThumbnail) {
         var url = data.path
         url += "." + data.extension
         view.loadImageFromInternet(url!!,view)
     }
}