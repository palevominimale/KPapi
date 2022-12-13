package com.junopark.kpapi.data.api

import com.junopark.kpapi.data.models.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRequest {

    @GET("films/top")
    fun getTop(
        @Query("type") type: String = TOP_250,
        @Query("page") page: Int = 1,
    ): Call<ListResponse>

    companion object {
        const val BASE_URL = """https://kinopoiskapiunofficial.tech/api/v2.2/"""
        const val TOP_250 = "TOP_250_BEST_FILMS"
        const val TOP_100_POPULAR = "TOP_100_POPULAR_FILMS"
        const val TOP_AWAIT = "TOP_AWAIT_FILMS"
    }
}