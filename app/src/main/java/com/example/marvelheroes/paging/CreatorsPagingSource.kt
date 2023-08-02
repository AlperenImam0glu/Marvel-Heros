package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.CreatorResults
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.util.Enums

class CreatorsPagingSource(
    private val marvelApi: RetrofitService,
    private val type: Enums,
    private val id: String = "0"
) : PagingSource<Int, CreatorResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CreatorResults> {
        return try {
            when (type) {
                Enums.Home -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCreatorsWithPage(position)
                    response.code?.let {
                        if(response.code==200){

                        }
                    }
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Comic -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCreatorsOfComics(id, position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Event -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCreatorsOfEvents(id, position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                else -> {
                    LoadResult.Error(Exception())
                }
            }
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