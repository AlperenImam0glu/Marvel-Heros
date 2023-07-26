package com.example.marvelheroes.adapter.itemAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.databinding.HeaderBinding

class HeaderAdapter() : RecyclerView.Adapter<HeaderAdapter.HeaderDesign>() {

    class HeaderDesign(binding: HeaderBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderDesign {
        val layoutInflater = LayoutInflater.from(parent.context)
        val headerBinding = HeaderBinding.inflate(layoutInflater, parent, false)
        return HeaderDesign(headerBinding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: HeaderDesign, position: Int) {

    }

}