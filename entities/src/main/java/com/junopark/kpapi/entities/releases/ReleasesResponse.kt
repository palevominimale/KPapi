package com.junopark.kpapi.entities.releases

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.films.ReleaseItem

data class ReleasesResponse(
    @SerializedName("releases" ) var releases : ArrayList<ReleaseItem> = arrayListOf(),
    @SerializedName("page"     ) var page     : Int?                = null,
    @SerializedName("total"    ) var total    : Int?                = null
)
