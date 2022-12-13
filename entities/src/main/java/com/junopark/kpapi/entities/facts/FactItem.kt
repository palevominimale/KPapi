package com.junopark.kpapi.entities.facts

import com.google.gson.annotations.SerializedName

data class FactItem(
    @SerializedName("text"    ) var text    : String?  = null,
    @SerializedName("type"    ) var type    : String?  = null,
    @SerializedName("spoiler" ) var spoiler : Boolean? = null
)
