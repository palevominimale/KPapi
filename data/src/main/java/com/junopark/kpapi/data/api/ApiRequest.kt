package com.junopark.kpapi.data.api


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
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {

    @GET("films/top")
    fun getTop(
        @Query("type") type: String = TOP_250,
        @Query("page") page: Int = 1,
    ): Call<com.junopark.kpapi.entities.ListResponse>

    @GET("films/{id}")
    fun getById(@Path("id") id: Int) : Call<FilmItemBig>

    @GET("films/{id}/seasons")
    fun getSeasons(@Path("id") id: Int) : Call<SeasonsResponse>

    @GET("films/{id}/facts")
    fun getFacts(@Path("id") id: Int) : Call<FactsResponse>

    @GET("films/{id}/distributions")
    fun getDistributions(@Path("id") id: Int) : Call<DistributionResponse>

    @GET("films/{id}/box_office")
    fun getBoxOffice(@Path("id") id: Int) : Call<BoxOfficeResponse>

    @GET("films/{id}/awards")
    fun getAwards(@Path("id") id: Int) : Call<AwardsResponse>

    @GET("films/{id}/similars")
    fun getSimilar(@Path("id") id: Int) : Call<SimilarResponse>

    @GET("films/{id}/sequels_and_prequels")
    fun getRelated(@Path("id") id: Int) : Call<List<FilmItemMini>>

    @GET("films/filters")
    fun getFilters() : Call<FilterResponse>

    @GET("films?")
    fun getByFilter(
        @Query("countries") countries : String = "",
        @Query("genres") genres : String = "",
        @Query("order") order : String = DEFAULT_ORDER,
        @Query("type") type : String = DEFAULT_TYPE,
        @Query("ratingFrom") ratingFrom : Int = 5,
        @Query("ratingTo") ratingTo : Int = 10,
        @Query("yearFrom") yearFrom : Int = 1990,
        @Query("yearTo") yearTo : Int = 2022,
        @Query("imdbId") imdbId : String = "",
        @Query("keyword") keyword : String = "",
        @Query("page") page : Int = 1,
    ) : Call<FilteredSearchResponse>

    @GET("films/search-by-keyword?")
    fun getByKeywordSearch(
        @Query("query") query : String = "",
        @Query("page") page : Int = 1,
    ) : Call<KeywordSearchResponse>

    @GET("films/releases?")
    fun getReleases(
        @Query("year") year : Int = 2022,
        @Query("month") month : String = "JANUARY",
        @Query("page") page : Int = 1,
    ) : Call<ReleasesResponse>

    companion object {
        const val BASE_URL = """https://kinopoiskapiunofficial.tech/api/v2.2/"""
        const val TOP_250 = "TOP_250_BEST_FILMS"
        const val TOP_100_POPULAR = "TOP_100_POPULAR_FILMS"
        const val TOP_AWAIT = "TOP_AWAIT_FILMS"
        const val DEFAULT_ORDER = "RATING"
        const val DEFAULT_TYPE = "FILM"
    }
}