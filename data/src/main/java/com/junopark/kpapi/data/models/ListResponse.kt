package com.junopark.kpapi.data.models

import com.google.gson.annotations.SerializedName

data class ListResponse(

    @SerializedName("pagesCount" ) var pagesCount : Int?             = null,
    @SerializedName("films"      ) var films      : ArrayList<Film>  = arrayListOf()

)
