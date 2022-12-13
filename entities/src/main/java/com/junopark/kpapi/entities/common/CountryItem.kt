package com.junopark.kpapi.entities.common

import com.google.gson.annotations.SerializedName

data class CountryItem(
    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("country" ) var country : String? = null
)
