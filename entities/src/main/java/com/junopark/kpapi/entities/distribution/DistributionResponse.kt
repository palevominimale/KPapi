package com.junopark.kpapi.entities.distribution

import com.google.gson.annotations.SerializedName

data class DistributionResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<DistributionItem> = arrayListOf()
)
