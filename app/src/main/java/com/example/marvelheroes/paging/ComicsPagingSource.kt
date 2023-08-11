package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.BuildConfig
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.util.Enums

class ComicsPagingSource(
    private val marvelApi: RetrofitService,
    private val type: Enums,
    private val id: String = "0",
    private val name: String = ""
) : PagingSource<Int, ComicsResults>() {

    private val api_key = BuildConfig.API_KEY
    private val ts = "1"
    private val hash = BuildConfig.HASH

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicsResults> {
        return try {
            when (type) {
                Enums.Home -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllComicsWithPage(ts, api_key, hash, position)

                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Character -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response =
                        marvelApi.getAllComicsOfCharacter(id, ts, api_key, hash, position)

                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }

                Enums.Event -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllComicsOfEvents(id, ts, api_key, hash, position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )

                }

                Enums.Creator -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllComicsOfCreators(id, ts, api_key, hash, position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )

                }

                Enums.Series -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllComicsOfSeries(id, ts, api_key, hash, position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )

                }

                Enums.Story -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllComicsOfStories(id, ts, api_key, hash, position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position < 20) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )

                }

                Enums.Search -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getComicsWithName(ts, api_key, hash, position, name)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
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


    override fun getRefreshKey(state: PagingState<Int, ComicsResults>): Int? {
        return FIRST_PAGE_INDEX
    }


}