package com.junopark.kpapi.data.models.awards

import com.google.gson.annotations.SerializedName

data class AwardsResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<AwardItem> = arrayListOf()
)
