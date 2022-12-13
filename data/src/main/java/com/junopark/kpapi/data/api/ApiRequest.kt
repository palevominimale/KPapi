package com.junopark.kpapi.data.api


import com.junopark.kpapi.entities.ListResponse
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
import com.junopark.kpapi.entities.staff.StaffItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {

    @GET("v2.2/films/top")
    fun getTop(
        @Query("type") type: String = TOP_250,
        @Query("page") page: Int = 1,
    ): Call<ListResponse>

    @GET("v2.2/films/{id}")
    fun getById(@Path("id") id: Int) : Call<FilmItemBig>

    @GET("v2.2/films/{id}/seasons")
    fun getSeasons(@Path("id") id: Int) : Call<SeasonsResponse>

    @GET("v2.2/films/{id}/facts")
    fun getFacts(@Path("id") id: Int) : Call<FactsResponse>

    @GET("v2.2/films/{id}/distributions")
    fun getDistributions(@Path("id") id: Int) : Call<DistributionResponse>

    @GET("v2.2/films/{id}/box_office")
    fun getBoxOffice(@Path("id") id: Int) : Call<BoxOfficeResponse>

    @GET("v2.2/films/{id}/awards")
    fun getAwards(@Path("id") id: Int) : Call<AwardsResponse>

    @GET("v2.2/films/{id}/similars")
    fun getSimilar(@Path("id") id: Int) : Call<SimilarResponse>

    @GET("v2.2/films/{id}/sequels_and_prequels")
    fun getRelated(@Path("id") id: Int) : Call<List<FilmItemMini>>

    @GET("v2.2/films/filters")
    fun getFilters() : Call<FilterResponse>

    @GET("v2.2/films?")
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

    @GET("v2.2/films/search-by-keyword?")
    fun getByKeywordSearch(
        @Query("query") query : String = "",
        @Query("page") page : Int = 1,
    ) : Call<KeywordSearchResponse>

    @GET("v2.2/films/releases?")
    fun getReleases(
        @Query("year") year : Int = 2022,
        @Query("month") month : String = "JANUARY",
        @Query("page") page : Int = 1,
    ) : Call<ReleasesResponse>

    @GET("v1/staff?")
    fun getStaff(
        @Query("filmId") id : Int = 301,
    ) : Call<List<StaffItem>>

    @GET("v1/persons?")
    fun getPersons(
        @Query("name") name : String = "",
        @Query("page") page : Int = 1,
    ) : Call<ReleasesResponse>

    companion object {
        const val BASE_URL = """https://kinopoiskapiunofficial.tech/api/"""
        const val TOP_250 = "TOP_250_BEST_FILMS"
        const val DEFAULT_ORDER = "RATING"
        const val DEFAULT_TYPE = "FILM"
    }
}