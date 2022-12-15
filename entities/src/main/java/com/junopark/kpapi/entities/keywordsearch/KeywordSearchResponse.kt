package com.junopark.kpapi.entities.keywordsearch

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.films.FilmItemBig

data class KeywordSearchResponse(
    @SerializedName("keyword"                ) var keyword                : String?                         = null,
    @SerializedName("pagesCount"             ) var pagesCount             : Int?                            = null,
    @SerializedName("films"                  ) var films                  : ArrayList<FilmItemBig>   = arrayListOf(),
    @SerializedName("searchFilmsCountResult" ) var searchFilmsCountResult : Int?                            = null
)
