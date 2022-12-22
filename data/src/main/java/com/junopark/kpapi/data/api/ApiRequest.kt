package com.junopark.kpapi.data.api


import com.junopark.kpapi.entities.ListResponse
import com.junopark.kpapi.entities.awards.AwardsResponse
import com.junopark.kpapi.entities.boxoffice.BoxOfficeResponse
import com.junopark.kpapi.entities.distribution.DistributionResponse
import com.junopark.kpapi.entities.facts.FactsResponse
import com.junopark.kpapi.entities.films.FilmItemBig
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
        @Query("type") type: String? = null,
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

    @GET("v2.1/films/{id}/sequels_and_prequels")
    fun getRelated(@Path("id") id: Int) : Call<List<FilmItemBig>>

    @GET("v2.2/films/filters")
    fun getFilters() : Call<FilterResponse>

    @GET("v2.2/films?")
    fun getByFilter(
        @Query("countries") countries : String? = null,
        @Query("genres") genres : String? = null,
        @Query("order") order : String? = null,
        @Query("type") type : String? = null,
        @Query("ratingFrom") ratingFrom : Int? = null,
        @Query("ratingTo") ratingTo : Int? = null,
        @Query("yearFrom") yearFrom : Int? = null,
        @Query("yearTo") yearTo : Int? = null,
        @Query("imdbId") imdbId : String? = null,
        @Query("keyword") keyword : String? = null,
        @Query("page") page : Int? = null,
    ) : Call<FilteredSearchResponse>

    @GET("v2.2/films?")
    fun getByImdbId(
        @Query("imdbId") imdbId : String? = null,
        @Query("page") page : Int? = 1,
    ) : Call<FilteredSearchResponse>

    @GET("v2.1/films/search-by-keyword?")
    fun getByKeywordSearch(
        @Query("keyword") query : String = " ",
        @Query("page") page : Int = 1,
    ) : Call<KeywordSearchResponse>

    @GET("v2.1/films/releases?")
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
    }
}