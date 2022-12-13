package com.junopark.kpapi.entities.common

import com.google.gson.annotations.SerializedName

data class GenreItem(
    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("genre" ) var genre : String? = null
)
