package com.junopark.kpapi.data.models.boxoffice

import com.google.gson.annotations.SerializedName

data class BoxOfficeResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<BoxOfficeItem> = arrayListOf()
)
