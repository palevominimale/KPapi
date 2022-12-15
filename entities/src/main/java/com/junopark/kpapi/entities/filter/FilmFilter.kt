package com.junopark.kpapi.entities.filter

data class FilmFilter(
    val countries : String = "",
    val genres : String = "",
    val order : String = "RATING",
    val type : String = "FILM",
    val ratingFrom : Int = 5,
    val ratingTo : Int = 10,
    val yearFrom : Int = 1990,
    val yearTo : Int = 2022,
    val imdbId : String = "",
    val keyword : String = "",
)
