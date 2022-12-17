package com.junopark.kpapi.entities.prefs

import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem
import com.junopark.kpapi.entities.filter.FilmFilter

data class PrefsDTO(
    val genres: List<GenreItem>,
    val countries: List<CountryItem>,
    val filter: FilmFilter
)
