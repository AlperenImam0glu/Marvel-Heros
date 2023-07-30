package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.Results
import com.example.marvelheroes.paging.network.RetrofitService

class ComicsPagingSource(private val marvelApi: RetrofitService,private val type:Int,private val id:String ="0") : PagingSource<Int, ComicsResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicsResults> {
        return try {
            if(type==0){
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsWithPage(position)
                Log.e("comics","$position - ${response.data!!.total!!}")
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20)
            }
            else{

                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsWithId(id,position)
                Log.e("comics","$position - ${response.data!!.total!!}")
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position <20 ) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }

        } catch (e: Exception) {
            Log.e("comics",e.message.toString())
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }



    override fun getRefreshKey(state: PagingState<Int, ComicsResults>): Int? {
        TODO("Not yet implemented")
    }


}