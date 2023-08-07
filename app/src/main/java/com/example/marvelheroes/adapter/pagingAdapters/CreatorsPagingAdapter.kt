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
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.safeNavigate
import com.example.marvelheroes.util.Enums
import com.example.marvelheroes.view.CharacterDetailPageFragmentDirections
import com.example.marvelheroes.view.HomePageFragmentDirections
import com.example.marvelheroes.viewmodel.SharedViewModel

class CreatorsPagingAdapter(var context: Context,val viewModel: SharedViewModel) :
    PagingDataAdapter<CreatorResults, CreatorsPagingAdapter.MyViewHolder>(DiffUtilCallBack()) {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HomepageCardDesignBinding.bind(view)
        fun bind(data: CreatorResults) {
            binding.cardTitle.text = data.firstName.toString()
            binding.cardSubtitle.text = data.id.toString()
            setImage(binding.imageView, data)
            binding.cardItemView.setOnClickListener {
                var newDataList = viewModel.getCreators() ?: ArrayList<CreatorResults>()
                newDataList.add(data)
                viewModel.setCreators(newDataList)

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


    fun setImage(view: ImageView, data: CreatorResults) {
        var url = data.thumbnail!!.path
        url += "." + data.thumbnail!!.extension
        view.loadImageFromInternet(url!!, view)
    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<CreatorResults>() {
        override fun areItemsTheSame(oldItem: CreatorResults, newItem: CreatorResults): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CreatorResults, newItem: CreatorResults): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}