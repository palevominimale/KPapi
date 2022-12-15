package com.junopark.kpapi.domain.interfaces

import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.domain.models.RoomResult
import com.junopark.kpapi.entities.films.FilmItemMini
import kotlinx.coroutines.flow.StateFlow

interface FavsFilmsDBRepo {

    val state : StateFlow<RoomResult>

    suspend fun getFilms()

    suspend fun getFilm(id: Int)

    suspend fun addFilm(filmItemMini: FilmItemMini)

    suspend fun RemoveFilm(filmItemMini: FilmItemMini)
}