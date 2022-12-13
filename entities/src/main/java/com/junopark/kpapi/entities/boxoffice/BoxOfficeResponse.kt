package com.junopark.kpapi.entities.boxoffice

import com.google.gson.annotations.SerializedName

data class BoxOfficeResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<BoxOfficeItem> = arrayListOf()
)
