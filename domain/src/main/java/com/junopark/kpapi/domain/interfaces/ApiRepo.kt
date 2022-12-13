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

    suspend fun getTop(type: String, page: Int)
    suspend fun getById(id: Int)
    suspend fun getSeasons(id: Int)
    suspend fun getFacts(id: Int)
    suspend fun getDistributions(id: Int)
    suspend fun getBoxOffice(id: Int)
    suspend fun getAwards(id: Int)
    suspend fun getSimilar(id: Int)
    suspend fun getRelated(id: Int)
    suspend fun getFilters()
    suspend fun getByFilter(
        countries : String = "",
        genres : String = "",
        order : String = DEFAULT_ORDER,
        type : String = DEFAULT_TYPE,
        ratingFrom : Int = 5,
        ratingTo : Int = 10,
        yearFrom : Int = 1990,
        yearTo : Int = 2022,
        imdbId : String = "",
        keyword : String = "",
        page : Int = 1,
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
    }

}