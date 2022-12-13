package com.junopark.kpapi.data.models.filter

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.data.models.common.CountryItem
import com.junopark.kpapi.data.models.common.GenreItem

data class FilterResponse(
    @SerializedName("genres"    ) var genres    : ArrayList<GenreItem>    = arrayListOf(),
    @SerializedName("countries" ) var countries : ArrayList<CountryItem> = arrayListOf()
)
