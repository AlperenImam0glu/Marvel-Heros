package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.BuildConfig
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.util.Enums

class CharacterPagingSource(private val marvelApi: RetrofitService,private val type:Enums,private val id:String="0",val name:String="") : PagingSource<Int, CharactersResults>() {

    private val api_key =BuildConfig.API_KEY
    private val ts="1"
    private val hash= BuildConfig.HASH
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResults> {
        return try {
            when (type) {
                Enums.Home -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersWithPage(ts,api_key,hash,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }
                Enums.Comic -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfComic(id,ts,api_key,hash,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Event -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfEvents(id,ts,api_key,hash,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Series -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfSeries(id,ts,api_key,hash,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Story -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfStories(id,ts,api_key,hash,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Search -> {

                    Log.e("arama","$name")
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getCharacterWithName(ts,api_key,hash,position,name)
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

    override fun getRefreshKey(state: PagingState<Int, CharactersResults>): Int? {
        return FIRST_PAGE_INDEX
    }


}