package com.example.marvelheroes.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelheroes.models.events.EventsResults
import com.example.marvelheroes.paging.network.RetrofitService

class EventsPagingSource(private val marvelApi: RetrofitService,private val type:Int,private val id:String ="0") : PagingSource<Int, EventsResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventsResults> {
        return try {
            if(type==0){
                //homepage
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllEventsWithPage(position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )

            }else if (type ==1){
                //character
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllEventsOfCharacter(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )

            }
            else if(type ==2 ){
                //comics
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllEventsOfComics(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }else if(type ==3){
                //comics
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllEventsOfCreators(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
            }else if(type ==4){
                //series
                val position = params.key ?: FIRST_PAGE_INDEX
                val response = marvelApi.getAllEventsOfSeries(id,position)
                LoadResult.Page(
                    data = response.data!!.results,
                    prevKey = if (position < 20) null else position - 20,
                    nextKey = if (position > response.data!!.total!!) null else position + 20
                )
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


    override fun getRefreshKey(state: PagingState<Int, EventsResults>): Int? {
        TODO("Not yet implemented")
    }


}