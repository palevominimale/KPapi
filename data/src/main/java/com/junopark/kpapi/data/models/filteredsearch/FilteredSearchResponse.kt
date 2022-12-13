package com.junopark.kpapi.data.models.filteredsearch

import com.google.gson.annotations.SerializedName

data class FilteredSearchResponse(
    @SerializedName("total"      ) var total      : Int?             = null,
    @SerializedName("totalPages" ) var totalPages : Int?             = null,
    @SerializedName("items"      ) var items      : ArrayList<FilteredSearchItem> = arrayListOf()
)
