package com.example.marvelheroes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.Results
import com.example.marvelheroes.paging.network.RetrofitService

class CreatorsPagingSource(private val marvelApi: RetrofitService) : PagingSource<Int, CreatorResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CreatorResults> {
        return try {
            val position = params.key ?: FIRST_PAGE_INDEX
            val response = marvelApi.getAllCreatorsWithPage(position)
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


    override fun getRefreshKey(state: PagingState<Int, CreatorResults>): Int? {
        TODO("Not yet implemented")
    }


}