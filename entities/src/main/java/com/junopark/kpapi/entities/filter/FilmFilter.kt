package com.junopark.kpapi.entities.filter

data class FilmFilter(
    val countries : String? = null,
    val genres : String? = null,
    val order : String? = null,
    val type : String? = null,
    val ratingFrom : Int? = null,
    val ratingTo : Int? = null,
    val yearFrom : Int? = null,
    val yearTo : Int? = null,
    val imdbId : String? = null,
    val keyword : String? = null,
)
