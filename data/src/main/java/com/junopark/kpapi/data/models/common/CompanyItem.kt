package com.junopark.kpapi.data.models.common

import com.google.gson.annotations.SerializedName

data class CompanyItem(
    @SerializedName("name" ) var name : String? = null
)
