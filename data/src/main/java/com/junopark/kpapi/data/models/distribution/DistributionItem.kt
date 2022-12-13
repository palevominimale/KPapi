package com.junopark.kpapi.data.models.distribution

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.data.models.common.CompanyItem
import com.junopark.kpapi.data.models.common.CountryItem

data class DistributionItem(
    @SerializedName("type"      ) var type      : String?              = null,
    @SerializedName("subType"   ) var subType   : String?              = null,
    @SerializedName("date"      ) var date      : String?              = null,
    @SerializedName("reRelease" ) var reRelease : Boolean?             = null,
    @SerializedName("country"   ) var countryItem   : CountryItem?             = CountryItem(),
    @SerializedName("companies" ) var companies : ArrayList<CompanyItem> = arrayListOf()
)
