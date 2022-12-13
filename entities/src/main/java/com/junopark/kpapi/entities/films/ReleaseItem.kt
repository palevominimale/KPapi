package com.junopark.kpapi.entities.films

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem

data class ReleaseItem(
    @SerializedName("filmId"                      ) var filmId                      : Int?                      = null,
    @SerializedName("nameRu"                      ) var nameRu                      : String?                   = null,
    @SerializedName("nameEn"                      ) var nameEn                      : String?                   = null,
    @SerializedName("year"                        ) var year                        : Int?                      = null,
    @SerializedName("posterUrl"                   ) var posterUrl                   : String?                   = null,
    @SerializedName("posterUrlPreview"            ) var posterUrlPreview            : String?                   = null,
    @SerializedName("countries"                   ) var countries                   : ArrayList<CountryItem>    = arrayListOf(),
    @SerializedName("genres"                      ) var genres                      : ArrayList<GenreItem>      = arrayListOf(),
    @SerializedName("rating"                      ) var rating                      : Double?                   = null,
    @SerializedName("ratingVoteCount"             ) var ratingVoteCount             : Int?                      = null,
    @SerializedName("expectationsRating"          ) var expectationsRating          : Double?                   = null,
    @SerializedName("expectationsRatingVoteCount" ) var expectationsRatingVoteCount : Int?                      = null,
    @SerializedName("duration"                    ) var duration                    : Int?                      = null,
    @SerializedName("releaseDate"                 ) var releaseDate                 : String?                   = null
)