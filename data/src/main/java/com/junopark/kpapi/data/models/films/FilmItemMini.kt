package com.junopark.kpapi.data.models.films

import com.google.gson.annotations.SerializedName

data class FilmItemMini(
    @SerializedName("filmId"           ) var filmId           : Int?    = null,
    @SerializedName("nameRu"           ) var nameRu           : String? = null,
    @SerializedName("nameEn"           ) var nameEn           : String? = null,
    @SerializedName("nameOriginal"     ) var nameOriginal     : String? = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String? = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String? = null,
    @SerializedName("relationType"     ) var relationType     : String? = null

)
