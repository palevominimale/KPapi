package com.junopark.kpapi.entities.common

import com.google.gson.annotations.SerializedName

data class CompanyItem(
    @SerializedName("name" ) var name : String? = null
)
