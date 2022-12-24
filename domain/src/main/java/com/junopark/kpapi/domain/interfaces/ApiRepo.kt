package com.junopark.kpapi.domain.interfaces

import androidx.paging.PagingData
import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.entities.films.FilmItemBig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ApiRepo {

    val state : StateFlow<ApiResult>
    val listFlow : Flow<PagingData<FilmItemBig>>

    suspend fun getTop(type: String = TOP_250, page: Int = 1)
    suspend fun getById(id: Int = DEFAULT_ID)
    suspend fun getSeasons(id: Int = DEFAULT_ID)
    suspend fun getFacts(id: Int = DEFAULT_ID)
    suspend fun getDistributions(id: Int = DEFAULT_ID)
    suspend fun getBoxOffice(id: Int = DEFAULT_ID)
    suspend fun getAwards(id: Int = DEFAULT_ID)
    suspend fun getSimilar(id: Int = DEFAULT_ID)
    suspend fun getRelated(id: Int = DEFAULT_ID)
    suspend fun getFilters()
    suspend fun getByFilter(
        countries : String? = null,
        genres : String? = null,
        order : String? = null,
        type : String? = null,
        ratingFrom : Int? = null,
        ratingTo : Int? = null,
        yearFrom : Int? = null,
        yearTo : Int? = null,
        imdbId : String? = null,
        keyword : String? = null,
        page : Int? = 1,
    )
    suspend fun getByKeywordSearch(
        query : String = "",
        page : Int = 1,
    )
    suspend fun getReleases(
        year : Int = 2022,
        month : String = "JANUARY",
        page : Int = 1,
    )

    companion object {
        const val DEFAULT_ID = 301
        const val TOP_250 = "TOP_250_BEST_FILMS"
    }

}