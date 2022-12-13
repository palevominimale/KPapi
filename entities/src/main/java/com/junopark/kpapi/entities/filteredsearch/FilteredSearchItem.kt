package com.junopark.kpapi.entities.filteredsearch

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem

data class FilteredSearchItem(
    @SerializedName("kinopoiskId"      ) var kinopoiskId      : Int?                    = null,
    @SerializedName("imdbId"           ) var imdbId           : String?                 = null,
    @SerializedName("nameRu"           ) var nameRu           : String?                 = null,
    @SerializedName("nameEn"           ) var nameEn           : String?                 = null,
    @SerializedName("nameOriginal"     ) var nameOriginal     : String?                 = null,
    @SerializedName("countries"        ) var countries        : ArrayList<CountryItem>  = arrayListOf(),
    @SerializedName("genres"           ) var genres           : ArrayList<GenreItem>    = arrayListOf(),
    @SerializedName("ratingKinopoisk"  ) var ratingKinopoisk  : Double?                 = null,
    @SerializedName("ratingImdb"       ) var ratingImdb       : Double?                 = null,
    @SerializedName("year"             ) var year             : Int?                    = null,
    @SerializedName("type"             ) var type             : String?                 = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?                 = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?                 = null,
)