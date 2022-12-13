package com.junopark.kpapi.entities.persons

import com.google.gson.annotations.SerializedName

data class PersonsResponse(
    @SerializedName("total" ) var total : Int?                      = null,
    @SerializedName("items" ) var items : ArrayList<PersonItem >    = arrayListOf()
)
