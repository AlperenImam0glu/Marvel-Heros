package com.example.marvelheroes.adapter.itemAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.adapter.CharacterPagingAdapter
import com.example.marvelheroes.databinding.DenemeKarakterListesiBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharaterListAdapter(var context: Context) :
    RecyclerView.Adapter<CharaterListAdapter.ViewHolder>() {

    var CharacterPagingAdapter = CharacterPagingAdapter(context)

    inner class ViewHolder(var binding: DenemeKarakterListesiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.rv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = CharacterPagingAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = DenemeKarakterListesiBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}