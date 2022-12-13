package com.junopark.kpapi.data.models.seasons

import com.google.gson.annotations.SerializedName

data class EpisodeItem(
    @SerializedName("seasonNumber"  ) var seasonNumber  : Int?    = null,
    @SerializedName("episodeNumber" ) var episodeNumber : Int?    = null,
    @SerializedName("nameRu"        ) var nameRu        : String? = null,
    @SerializedName("nameEn"        ) var nameEn        : String? = null,
    @SerializedName("synopsis"      ) var synopsis      : String? = null,
    @SerializedName("releaseDate"   ) var releaseDate   : String? = null
)
