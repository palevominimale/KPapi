package com.junopark.kpapi.data.models.releases

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.data.models.films.ReleaseItem

data class ReleasesResponse(
    @SerializedName("releases" ) var releases : ArrayList<ReleaseItem> = arrayListOf(),
    @SerializedName("page"     ) var page     : Int?                = null,
    @SerializedName("total"    ) var total    : Int?                = null
)
