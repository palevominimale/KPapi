package com.junopark.kpapi.entities.filter

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem

data class FilterResponse(
    @SerializedName("genres"    ) var genres    : ArrayList<GenreItem>    = arrayListOf(),
    @SerializedName("countries" ) var countries : ArrayList<CountryItem> = arrayListOf()
)
