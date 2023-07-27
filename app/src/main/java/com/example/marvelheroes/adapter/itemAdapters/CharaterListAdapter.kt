package com.example.marvelheroes.adapter.itemAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.adapter.pagingAdapters.CharacterPagingAdapter
import com.example.marvelheroes.databinding.HomepageMainRecyclerviewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharaterListAdapter(var context: Context, var title: String) :
    RecyclerView.Adapter<CharaterListAdapter.ViewHolder>() {

    var characterPagingAdapter = CharacterPagingAdapter(context)

    inner class ViewHolder(var binding: HomepageMainRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.rv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = characterPagingAdapter
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = HomepageMainRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.heroListTitle.text = title
        holder.bind()
    }
}