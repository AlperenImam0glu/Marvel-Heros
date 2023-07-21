package com.example.marvelheroes.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.HomePageCardDesignBinding

class RecyclerViewAdapter(var context: Context) : PagingDataAdapter<Results, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private  val binding = HomePageCardDesignBinding.bind(view)

        fun bind(data: Results){
         binding.cardTitle.text = data.name.toString()

            setImage(binding,data)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =  LayoutInflater.from(parent.context).inflate(R.layout.home_page_card_design,parent,false)
        return  MyViewHolder(inflater)
    }


    fun setImage(bindig: HomePageCardDesignBinding, data: Results) {

        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        var containsString = false

        url?.let {
            containsString = url!!.contains("image_not_available")
        }

        if(containsString){
            bindig.layout.setBackgroundResource(R.drawable.image_not_available)
        }else{
            Glide.with(context)
                .load(url)
                .into(object : CustomTarget<Drawable?>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        bindig.layout.background = resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                })
        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Results>(){
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
           return  oldItem == newItem
        }

    }
}