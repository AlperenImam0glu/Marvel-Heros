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
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.ItemImageViewBinding


class CardItemAdapter(val characterList: ArrayList<Results>,val context:Context ) :RecyclerView.Adapter<CardItemAdapter.CardViewHolder>() {

    class CardViewHolder(val binding : ItemImageViewBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = DataBindingUtil.inflate<ItemImageViewBinding>(LayoutInflater.from(parent.context),
            R.layout.item_image_view,parent,false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.cardSubtitle.text = characterList[position].id.toString()
        holder.binding.cardTitle.text = characterList[position].name

        setImage(holder,position)

     }

    fun updateCountryList(newList:List<Results>){
        characterList.clear()
        characterList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setImage(holder: CardViewHolder,position: Int){

        var url = characterList[position].thumbnail!!.path
        url += "."+characterList[position].thumbnail!!.extension



        Glide.with(context)
            .load(url)
            .into(object : CustomTarget<Drawable?>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    holder.binding.layout.background=resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }


            })

    }

}