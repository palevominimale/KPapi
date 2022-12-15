package com.junopark.kpapi.entities.top

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem

data class TopItem(
    @SerializedName("filmId"           ) var filmId           : Int?                    = null,
    @SerializedName("nameRu"           ) var nameRu           : String?                 = null,
    @SerializedName("nameEn"           ) var nameEn           : String?                 = null,
    @SerializedName("year"             ) var year             : String?                 = null,
    @SerializedName("filmLength"       ) var filmLength       : String?                 = null,
    @SerializedName("countries"        ) var countries        : ArrayList<CountryItem>  = arrayListOf(),
    @SerializedName("genres"           ) var genres           : ArrayList<GenreItem>    = arrayListOf(),
    @SerializedName("rating"           ) var rating           : String?                 = null,
    @SerializedName("ratingVoteCount"  ) var ratingVoteCount  : Int?                    = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?                 = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?                 = null,
    @SerializedName("ratingChange"     ) var ratingChange     : String?                 = null
)
