package com.junopark.kpapi.entities.boxoffice

import com.google.gson.annotations.SerializedName

data class BoxOfficeItem(
    @SerializedName("type"         ) var type         : String? = null,
    @SerializedName("amount"       ) var amount       : Int?    = null,
    @SerializedName("currencyCode" ) var currencyCode : String? = null,
    @SerializedName("name"         ) var name         : String? = null,
    @SerializedName("symbol"       ) var symbol       : String? = null
)
