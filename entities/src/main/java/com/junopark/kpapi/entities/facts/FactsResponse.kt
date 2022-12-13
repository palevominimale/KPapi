package com.junopark.kpapi.entities.facts

import com.google.gson.annotations.SerializedName

data class FactsResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<FactItem> = arrayListOf()
)
