package com.junopark.kpapi.domain.interfaces

import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.entities.awards.AwardsResponse
import com.junopark.kpapi.entities.boxoffice.BoxOfficeResponse
import com.junopark.kpapi.entities.distribution.DistributionResponse
import com.junopark.kpapi.entities.facts.FactsResponse
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.films.FilmItemMini
import com.junopark.kpapi.entities.filter.FilterResponse
import com.junopark.kpapi.entities.filteredsearch.FilteredSearchResponse
import com.junopark.kpapi.entities.keywordsearch.KeywordSearchResponse
import com.junopark.kpapi.entities.releases.ReleasesResponse
import com.junopark.kpapi.entities.seasons.SeasonsResponse
import com.junopark.kpapi.entities.similar.SimilarResponse
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
        const val DEFAULT_ORDER = "RATING"
        const val DEFAULT_TYPE = "FILM"
        const val DEFAULT_ID = 301
        const val TOP_250 = "TOP_250_BEST_FILMS"
    }

}