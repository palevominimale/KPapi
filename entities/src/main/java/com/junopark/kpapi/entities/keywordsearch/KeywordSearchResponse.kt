package com.junopark.kpapi.entities.keywordsearch

import com.google.gson.annotations.SerializedName

data class KeywordSearchResponse(
    @SerializedName("keyword"                ) var keyword                : String?                         = null,
    @SerializedName("pagesCount"             ) var pagesCount             : Int?                            = null,
    @SerializedName("films"                  ) var films                  : ArrayList<FilmItemFromSearch>   = arrayListOf(),
    @SerializedName("searchFilmsCountResult" ) var searchFilmsCountResult : Int?                            = null
)
