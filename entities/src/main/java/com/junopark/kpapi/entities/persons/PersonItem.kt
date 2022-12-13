package com.junopark.kpapi.entities.persons

import com.google.gson.annotations.SerializedName

data class PersonItem(
    @SerializedName("kinopoiskId" ) var kinopoiskId : Int?    = null,
    @SerializedName("webUrl"      ) var webUrl      : String? = null,
    @SerializedName("nameRu"      ) var nameRu      : String? = null,
    @SerializedName("nameEn"      ) var nameEn      : String? = null,
    @SerializedName("sex"         ) var sex         : String? = null,
    @SerializedName("posterUrl"   ) var posterUrl   : String? = null
)
