package com.example.marvelheroes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.CharactersResults
import com.example.marvelheroes.paging.network.RetrofitService
import com.example.marvelheroes.util.Enums

class CharacterPagingSource(private val marvelApi: RetrofitService,private val type:Enums,private  val id:String="0") : PagingSource<Int, CharactersResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResults> {
        return try {
            when (type) {
                Enums.Home -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersWithPage(position)

                    response.code?.let {
                        if(response.code==200){

                        }
                    }
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20
                    )
                }
                Enums.Comic -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfComic(id,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Event -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfEvents(id,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Series -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfSeries(id,position)
                    LoadResult.Page(
                        data = response.data!!.results,
                        prevKey = if (position == 1) null else position - 20,
                        nextKey = if (position > response.data!!.total!!) null else position + 20)
                }
                Enums.Story -> {
                    val position = params.key ?: FIRST_PAGE_INDEX
                    val response = marvelApi.getAllCharactersOfStories(id,position)
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
        TODO("Not yet implemented")
    }


}