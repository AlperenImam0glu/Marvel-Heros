package com.example.marvelheroes.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.HomePageCardDesignBinding
import com.example.marvelheroes.view.HomePageFragmentDirections


class CharacterAdapter(val characterList: ArrayList<Results>, val context: Context) :
    RecyclerView.Adapter<CharacterAdapter.CardViewHolder>() {

    class CardViewHolder(val binding: HomePageCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = DataBindingUtil.inflate<HomePageCardDesignBinding>(
            LayoutInflater.from(parent.context),
            R.layout.home_page_card_design, parent, false
        )
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.cardSubtitle.text = characterList[position].id.toString()
        holder.binding.cardTitle.text = characterList[position].name

        holder.binding.cardItemView.setOnClickListener {
            val action = HomePageFragmentDirections.actionHomePageFragmentToCharacterDetailPageFragment(characterList[position])
            Navigation.findNavController(it).navigate(action)
        }

        //setImageToCard(holder, position);
        setImage(holder, position)
    }

    fun updateCharacterList(newList: List<Results>) {
        characterList.clear()
        characterList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setImage(holder: CardViewHolder, position: Int) {

        var url = characterList[position].thumbnail!!.path
        url += "." + characterList[position].thumbnail!!.extension
        var containsString = false

        url?.let {
            containsString = url!!.contains("image_not_available")
        }

        if(containsString){
            holder.binding.layout.setBackgroundResource(R.drawable.image_not_available)
        }else{
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