package com.junopark.kpapi.data.models.seasons

import com.google.gson.annotations.SerializedName

data class EpisodesItem(
    @SerializedName("number"   ) var number   : Int?                = null,
    @SerializedName("episodes" ) var episodeItems : ArrayList<EpisodeItem> = arrayListOf()
)
