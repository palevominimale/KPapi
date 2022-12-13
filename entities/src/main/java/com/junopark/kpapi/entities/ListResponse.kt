package com.junopark.kpapi.entities

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.films.FilmItemBig

data class ListResponse(

    @SerializedName("pagesCount" ) var pagesCount : Int?             = null,
    @SerializedName("films"      ) var filmItemBigs      : ArrayList<FilmItemBig>  = arrayListOf()

)
