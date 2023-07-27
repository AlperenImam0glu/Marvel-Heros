package com.example.marvelheroes.adapter.pagingAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.R
import com.example.marvelheroes.Results
import com.example.marvelheroes.databinding.HomepageCardDesignBinding
import com.example.marvelheroes.loadImageFromInternet
import com.example.marvelheroes.view.HomePageFragmentDirections

class CharacterPagingAdapter(var context: Context) :
    PagingDataAdapter<Results, CharacterPagingAdapter.MyViewHolder>(DiffUtilCallBack()) {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HomepageCardDesignBinding.bind(view)

        fun bind(data: Results) {
            binding.cardTitle.text = data.name.toString()
            binding.cardSubtitle.text = data.id.toString()
            setImage(binding.imageView, data)
            binding.cardItemView.setOnClickListener {
                val action = HomePageFragmentDirections.actionHomePageFragmentToCharacterDetailPageFragment(data)
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.homepage_card_design, parent, false)
        return MyViewHolder(inflater)
    }


    fun setImage(view: ImageView, data: Results) {
        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        view.loadImageFromInternet(url!!,view)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }

    }
}