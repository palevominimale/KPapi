package com.junopark.kpapi.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("stations/search?")
    fun getListWithFilter(
        @Query("country") country: String,
        @Query("tagList") tagList: String,
        @Query("language") language: String,
        @Query("limit") limit: Int = 50
    ): Call<List<*>>

    companion object {
        const val BASE_URL = """http://89.58.16.19//json/"""
    }
}