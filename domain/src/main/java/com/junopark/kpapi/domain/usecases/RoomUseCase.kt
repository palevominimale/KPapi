package com.junopark.kpapi.domain.usecases

import com.junopark.kpapi.domain.interfaces.FavsFilmsDBRepo
import com.junopark.kpapi.entities.films.FilmItemBig


class RoomUseCase(
    private val favsFilms:FavsFilmsDBRepo
) {

    suspend fun getFilms() = favsFilms.getFilms()
    suspend fun getFilm(id:Int) = favsFilms.getFilm(id)
    suspend fun addFilm(filmItemBig: FilmItemBig) = favsFilms.addFilm(filmItemBig)
    suspend fun removeFilm(id: Int) = favsFilms.removeFilm(id)

}