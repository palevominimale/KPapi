package com.junopark.kpapi.entities.seasons

import com.google.gson.annotations.SerializedName

data class SeasonsResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<EpisodesItem> = arrayListOf()
)
