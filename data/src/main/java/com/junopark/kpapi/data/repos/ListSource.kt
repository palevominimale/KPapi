package com.junopark.kpapi.data.repos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.junopark.kpapi.entities.films.FilmItemBig

class ListSource (
    private val loader: suspend (Int) -> List<FilmItemBig>,
): PagingSource<Int, FilmItemBig>() {

    override fun getRefreshKey(state: PagingState<Int, FilmItemBig>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmItemBig> {
        return try {
            val page = params.key ?: 1
            val data = loader(page + 1)
            val from = params.loadSize * page
            LoadResult.Page(
                data = data,
                prevKey = if(page == 0) null else page -1 ,
                nextKey = if(data.isNotEmpty()) page + 1 else null,
                itemsBefore = from,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}