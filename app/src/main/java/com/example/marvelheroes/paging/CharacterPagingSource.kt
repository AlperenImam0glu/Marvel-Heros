package com.example.marvelheroes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.Results
import com.example.marvelheroes.paging.network.RetrofitService

class CharacterPagingSource(private val marvelApi: RetrofitService,private val type:Int,private  val id:String="0") : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        return try {
            if(type==0){
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllCharactersWithPage(position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position == 1) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }else{
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsCharacters(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position == 1) null else position - 20,
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

    override fun getRefreshKey(state: PagingState<Int, Results>): Int? {
        TODO("Not yet implemented")
    }


}