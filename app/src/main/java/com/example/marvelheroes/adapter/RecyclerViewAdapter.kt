package com.example.marvelheroes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.HomePageCardDesignBinding

class RecyclerViewAdapter : PagingDataAdapter<Results, RecyclerViewAdapter.MyViewHolder>(DiffUtilCallBack()) {
    inner class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private  val binding = HomePageCardDesignBinding.bind(view)

        fun bind(data: Results){
         binding.cardTitle.text = data.name.toString()
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =  LayoutInflater.from(parent.context).inflate(R.layout.home_page_card_design,parent,false)
        return  MyViewHolder(inflater)
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