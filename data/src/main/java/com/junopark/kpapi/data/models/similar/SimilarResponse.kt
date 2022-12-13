package com.junopark.kpapi.data.models.similar

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.data.models.films.FilmItemMini

data class SimilarResponse(
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("items" ) var items : ArrayList<FilmItemMini> = arrayListOf()
)