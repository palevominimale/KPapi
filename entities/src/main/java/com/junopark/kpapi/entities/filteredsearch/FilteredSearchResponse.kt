package com.junopark.kpapi.entities.filteredsearch

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.films.FilmItemBig

data class FilteredSearchResponse(
    @SerializedName("total"      ) var total      : Int?             = null,
    @SerializedName("totalPages" ) var totalPages : Int?             = null,
    @SerializedName("items"      ) var items      : ArrayList<FilmItemBig> = arrayListOf()
)
