package com.example.marvelheroes.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.ComicsResults
import com.example.marvelheroes.paging.network.RetrofitService

class ComicsPagingSource(private val marvelApi: RetrofitService,private val type:Int,private val id:String ="0") : PagingSource<Int, ComicsResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicsResults> {
        return try {
            if(type==0){
                //homepage
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsWithPage(position)

                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20)
            }
            else if (type==1){
                //characters
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsOfCharacter(id,position)

                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position <20 ) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            } else if (type==2){
                //events
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsOfEvents(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position <20 ) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20)

            }else if (type==3){
                //creators
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsOfCreators(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position <20 ) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20)

            }else if (type==4){
                //series
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsOfSeries(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position <20 ) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20)

            }else if (type==5){
                //series
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllComicsOfStories(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position <20 ) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20)

            }else{
                LoadResult.Error(Exception())
            }

        } catch (e: Exception) {
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