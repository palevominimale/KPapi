package com.junopark.kpapi.data.models

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.data.models.films.FilmItemBig

data class ListResponse(

    @SerializedName("pagesCount" ) var pagesCount : Int?             = null,
    @SerializedName("films"      ) var filmItemBigs      : ArrayList<FilmItemBig>  = arrayListOf()

)
