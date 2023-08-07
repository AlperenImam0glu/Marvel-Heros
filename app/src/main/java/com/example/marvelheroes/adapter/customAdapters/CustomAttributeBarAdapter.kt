package com.example.marvelheroes.adapter.customAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.R
import com.example.marvelheroes.databinding.DetailpageAttributeBarItemBinding


class CustomAttributeBarAdapter( var value: Int) :
    RecyclerView.Adapter<CustomAttributeBarAdapter.ViewHolder>() {
    val ITEM_COUNT = 44

    class ViewHolder(val binding: DetailpageAttributeBarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DetailpageAttributeBarItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var x = 150
        var y = x / ITEM_COUNT
        var z = (value / y).toInt()

        if (value == 0) {
            holder.binding.image.setImageResource(R.drawable.bar_8dp)
            holder.binding.image.alpha = 0.25f
        } else if (z > position) {
            holder.binding.image.setImageResource(R.drawable.bar_8dp)
            if (position == ITEM_COUNT - 1) {
                holder.binding.image.setImageResource(R.drawable.bar_12dp)
            }
        } else if (z == position) {
            holder.binding.image.setImageResource(R.drawable.bar_12dp)
        } else {
            holder.binding.image.setImageResource(R.drawable.bar_8dp)
            holder.binding.image.alpha = 0.25f
        }
    }

}
