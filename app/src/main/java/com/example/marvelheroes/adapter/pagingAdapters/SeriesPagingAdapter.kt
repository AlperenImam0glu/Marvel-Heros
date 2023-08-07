package com.example.marvelheroes.adapter.pagingAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.R
import com.example.marvelheroes.databinding.HomepageCardDesignBinding
import com.example.marvelheroes.loadImageFromInternet
import com.example.marvelheroes.safeNavigate
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.view.CharacterDetailPageFragmentDirections
import com.example.marvelheroes.view.HomePageFragmentDirections
import com.example.marvelheroes.viewmodel.SharedViewModel

class SeriesPagingAdapter(var context: Context,val viewModel:SharedViewModel) :
    PagingDataAdapter<SeriesResults, SeriesPagingAdapter.MyViewHolder>(DiffUtilCallBack()) {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HomepageCardDesignBinding.bind(view)
        fun bind(data: SeriesResults) {
            binding.cardTitle.text = data.title.toString()
            binding.cardSubtitle.text = data.id.toString()
            setImage(binding.imageView, data)
            binding.cardItemView.setOnClickListener {
                var newDataList = viewModel.getSeries() ?: ArrayList<SeriesResults>()
                newDataList.add(data)
                viewModel.setSeries(newDataList)

                viewModel.getCurrentPage()!!.value?.let { value ->
                    Navigation.findNavController(it).safeNavigate(value)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.homepage_card_design, parent, false)
        return MyViewHolder(inflater)
    }


    fun setImage(view: ImageView, data: SeriesResults) {
        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        view.loadImageFromInternet(url!!, view)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<SeriesResults>() {
        override fun areItemsTheSame(oldItem: SeriesResults, newItem: SeriesResults): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SeriesResults, newItem: SeriesResults): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}