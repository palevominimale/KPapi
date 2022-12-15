package com.junopark.kpapi.entities.top

import com.google.gson.annotations.SerializedName

data class TopResponse(
    @SerializedName("pagesCount" ) var pagesCount : Int?             = null,
    @SerializedName("films"      ) var films      : ArrayList<TopItem> = arrayListOf()
)