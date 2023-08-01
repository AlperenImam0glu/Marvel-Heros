package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.series.SeriesResults

class SeriesPagingSource(private val marvelApi: RetrofitService,private val type:Int,private val id:String ="0") : PagingSource<Int, SeriesResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesResults> {
        return try {
            if(type ==0){
                // homepage
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllSeriesWithPage(position)

                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }
            else if (type ==1){
                //character
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllSeriesOfCharacter(id, position)

                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }
            else if (type ==2){
                //creators
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllSeriesOfCreators(id, position)

                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            } else if (type ==3){
                //creators
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllSeriesOfStories(id, position)

                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            } else{
                LoadResult.Error(Exception())
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
    }


    override fun getRefreshKey(state: PagingState<Int, SeriesResults>): Int? {
        TODO("Not yet implemented")
    }


}