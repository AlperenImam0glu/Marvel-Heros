package com.example.marvelheroes.adapter.pagingAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.R
import com.example.marvelheroes.databinding.HomepageCardDesignBinding
import com.example.marvelheroes.loadImageFromInternet

class deneme(var context: Context) :
    PagingDataAdapter<ComicsResults, deneme.MyViewHolder>(DiffUtilCallBack()) {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HomepageCardDesignBinding.bind(view)

        fun bind(data: ComicsResults) {
            Log.e("hata","${data.title.toString()}")
            binding.cardTitle.text = data.title.toString()
            binding.cardSubtitle.text = data.id.toString()
            setImage(binding.imageView, data)

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("hata","deneme çalıştı")
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.homepage_card_design, parent, false)
        Log.e("hata","deneme çalıştı")
        return MyViewHolder(inflater)
    }


    fun setImage(view: ImageView, data: ComicsResults) {
        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        view.loadImageFromInternet(url!!,view)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<ComicsResults>() {
        override fun areItemsTheSame(oldItem: ComicsResults, newItem: ComicsResults): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ComicsResults, newItem: ComicsResults): Boolean {
            return oldItem == newItem
        }

    }
}