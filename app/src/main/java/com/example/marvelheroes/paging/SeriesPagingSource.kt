package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.BuildConfig
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.series.SeriesResults
import com.example.marvelheroes.util.Enums

class SeriesPagingSource(
    private val marvelApi: RetrofitService,
    private val type: Enums,
    private val id: String = "0",
    private val name:String=""
) : PagingSource<Int, SeriesResults>() {
    private val api_key = BuildConfig.API_KEY
    private val ts="1"
    private val hash= BuildConfig.HASH
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesResults> {
        return try {
            when (type) {
                Enums.Home -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllSeriesWithPage(ts,api_key,hash,position)

                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Character -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllSeriesOfCharacter(id,ts,api_key,hash,position)

                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Creator -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllSeriesOfCreators(id,ts,api_key,hash,position)

                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Story -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllSeriesOfStories(id,ts,api_key,hash,position)

                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }
                Enums.Search -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getSeriesWithName(ts,api_key,hash,position,name)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
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


    override fun getRefreshKey(state: PagingState<Int, SeriesResults>): Int? {
        TODO("Not yet implemented")
    }


}