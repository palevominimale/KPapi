package com.junopark.kpapi.entities.films

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class FilmItemMini(
    @SerializedName("filmId"           ) @PrimaryKey var filmId           : Int?    = null,
    @SerializedName("nameRu"           ) var nameRu           : String? = null,
    @SerializedName("nameEn"           ) var nameEn           : String? = null,
    @SerializedName("nameOriginal"     ) var nameOriginal     : String? = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String? = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String? = null,
    @SerializedName("relationType"     ) var relationType     : String? = null

)
