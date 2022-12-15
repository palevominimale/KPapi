package com.junopark.kpapi.domain.interfaces

import com.junopark.kpapi.domain.models.ApiResult
import kotlinx.coroutines.flow.StateFlow

interface ApiRepo {

    val state : StateFlow<ApiResult>

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
        ratingFrom : Int? = 0,
        ratingTo : Int? = 10,
        yearFrom : Int? = 1000,
        yearTo : Int? = 3000,
        imdbId : String? = null,
        keyword : String? = null,
        page : Int? = 1,
    )
    suspend fun getByFilter(
        order : String? = null,
        type : String? = null,
        ratingFrom : Int? = 0,
        ratingTo : Int? = 10,
        yearFrom : Int? = 1000,
        yearTo : Int? = 3000,
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