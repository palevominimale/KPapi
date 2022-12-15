package com.junopark.kpapi.domain.usecases

import com.junopark.kpapi.domain.interfaces.FavsFilmsDBRepo
import com.junopark.kpapi.entities.films.FilmItemMini

class RoomUseCase(
    private val favsFilms:FavsFilmsDBRepo
) {

    suspend fun getFilms() = favsFilms.getFilms()
    suspend fun getFilm(id:Int?) = favsFilms.getFilm(id)
    suspend fun addFilm(filmItemMini:FilmItemMini) = favsFilms.addFilm(filmItemMini)
    suspend fun removeFilm(id: Int) = favsFilms.RemoveFilm(id)

}