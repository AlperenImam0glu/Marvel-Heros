package com.example.marvelheroes.adapter.pagingAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelheroes.R
import com.example.marvelheroes.databinding.HomepageCardDesignBinding
import com.example.marvelheroes.loadImageFromInternet
import com.example.marvelheroes.stories.StoriesResults

class StoriesPagingAdapter(var context: Context) :
    PagingDataAdapter<StoriesResults, StoriesPagingAdapter.MyViewHolder>(DiffUtilCallBack()) {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = HomepageCardDesignBinding.bind(view)
        fun bind(data: StoriesResults) {
            binding.cardTitle.text = data.title.toString()
            binding.cardSubtitle.text = data.id.toString()
            setImage(binding.imageView, data)
            binding.cardItemView.setOnClickListener {
                /*
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToCharacterDetailPageFragment(
                        data
                    )
                Navigation.findNavController(it).navigate(action)*/
                //comics detail ekranı hazırla
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.homepage_card_design, parent, false)
        return MyViewHolder(inflater)
    }


    fun setImage(view: ImageView, data: StoriesResults) {

        if(data.thumbnail != null){
            data.thumbnail?.let {
                var url = data.thumbnail!!.path
                url += "." + data.thumbnail!!.extension
                view.loadImageFromInternet(url!!, view)
            }
        }else{
            view.setImageResource(R.drawable.portrait_xlarge).apply {
                view.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }


    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<StoriesResults>() {
        override fun areItemsTheSame(oldItem: StoriesResults, newItem: StoriesResults): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StoriesResults, newItem: StoriesResults): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}