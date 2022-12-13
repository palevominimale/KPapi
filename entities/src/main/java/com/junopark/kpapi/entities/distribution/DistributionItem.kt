package com.junopark.kpapi.entities.distribution

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.CompanyItem
import com.junopark.kpapi.entities.common.CountryItem

data class DistributionItem(
    @SerializedName("type"      ) var type      : String?              = null,
    @SerializedName("subType"   ) var subType   : String?              = null,
    @SerializedName("date"      ) var date      : String?              = null,
    @SerializedName("reRelease" ) var reRelease : Boolean?             = null,
    @SerializedName("country"   ) var countryItem   : CountryItem?             = CountryItem(),
    @SerializedName("companies" ) var companies : ArrayList<CompanyItem> = arrayListOf()
)
