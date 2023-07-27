package com.example.marvelheroes.adapter.itemAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.adapter.pagingAdapters.EventsPagingAdapter
import com.example.marvelheroes.databinding.HomepageMainRecyclerviewBinding

class EventListAdapter(var context: Context,var title: String) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    var evetPagingAdapter = EventsPagingAdapter(context)

    inner class ViewHolder(var binding: HomepageMainRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.rv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = evetPagingAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = HomepageMainRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.heroListTitle.text = title
        holder.bind()
    }

    override fun getItemCount(): Int = 1

}