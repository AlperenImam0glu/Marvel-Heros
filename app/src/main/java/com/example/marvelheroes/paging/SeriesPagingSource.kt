package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.series.SeriesResults

class SeriesPagingSource(private val marvelApi: RetrofitService) : PagingSource<Int, SeriesResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesResults> {
        return try {
            val position = params.key ?: FIRST_PAGE_INDEX
            val response = marvelApi.getAllSeriesWithPage(position)
            Log.e("hata","$position - ${response.data!!.total!!}")
            LoadResult.Page(
                data = response.data!!.results,
                prevKey = if (position < 20) null else position - 20,
                nextKey = if (position > response.data!!.total!!) null else position + 20
            )


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