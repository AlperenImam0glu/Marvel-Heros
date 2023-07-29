package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.stories.StoriesResults


class StoriesPagingSource(private val marvelApi: RetrofitService,private val type:Int,private val id:String ="0") : PagingSource<Int, StoriesResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoriesResults> {
        return try {
            if(type ==0 ){
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllStoriesWithPage(position)
                Log.e("hata","$position - ${response.data!!.total!!}")
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }else{
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllStoriesWithId(id,position)
                Log.e("hata","$position - ${response.data!!.total!!}")
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }


    override fun getRefreshKey(state: PagingState<Int, StoriesResults>): Int? {
        TODO("Not yet implemented")
    }


}