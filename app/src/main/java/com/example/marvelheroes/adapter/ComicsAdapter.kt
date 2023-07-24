package com.example.marvelheroes.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.R
import com.example.marvelheroes.databinding.HomePageCardDesignBinding

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
        setImage(holder, position)
    }

    fun updateCharacterList(newList: List<ComicsResults>) {
        comicsList.clear()
        comicsList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setImage(holder: CardViewHolder, position: Int) {

        var url = comicsList[position].thumbnail!!.path
        url += "." + comicsList[position].thumbnail!!.extension
        var containsString = false

        url?.let {
            containsString = url!!.contains("image_not_available")
        }
        if(containsString){
            holder.binding.layout.setBackgroundResource(R.drawable.image_not_available)
        }
        else{
            Glide.with(context)
                .load(url)
                .into(object : CustomTarget<Drawable?>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        holder.binding.layout.background = resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }
}