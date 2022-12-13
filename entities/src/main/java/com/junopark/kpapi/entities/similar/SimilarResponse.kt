package com.junopark.kpapi.entities.similar

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.films.FilmItemMini

data class SimilarResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<FilmItemMini> = arrayListOf()
)