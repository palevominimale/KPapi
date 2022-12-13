package com.junopark.kpapi.data.models.distribution

import com.google.gson.annotations.SerializedName

data class DistributionResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<DistributionItem> = arrayListOf()
)
