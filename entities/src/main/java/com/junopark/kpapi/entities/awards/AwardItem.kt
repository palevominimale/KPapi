package com.junopark.kpapi.entities.awards

import com.google.gson.annotations.SerializedName
import com.junopark.kpapi.entities.common.PersonItem

data class AwardItem(
    @SerializedName("name"           ) var name           : String?            = null,
    @SerializedName("win"            ) var win            : Boolean?           = null,
    @SerializedName("imageUrl"       ) var imageUrl       : String?            = null,
    @SerializedName("nominationName" ) var nominationName : String?            = null,
    @SerializedName("year"           ) var year           : Int?               = null,
    @SerializedName("persons"        ) var persons        : ArrayList<PersonItem> = arrayListOf()
)
