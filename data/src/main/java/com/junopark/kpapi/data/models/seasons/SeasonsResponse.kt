package com.junopark.kpapi.data.models.seasons

import com.google.gson.annotations.SerializedName

data class SeasonsResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<EpisodesItem> = arrayListOf()
)
