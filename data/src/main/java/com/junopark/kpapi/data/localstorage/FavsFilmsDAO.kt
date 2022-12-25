package com.junopark.kpapi.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.junopark.kpapi.entities.films.FilmItemBig


@Dao
interface FavsFilmsDAO {

    @Query("SELECT * FROM filmItemBig")
    suspend fun getFilms(): List<FilmItemBig>

    @Query("SELECT * FROM FilmItemBig WHERE filmId=(:id)")
    suspend fun getFilm(id: Int?): FilmItemBig?

    @Insert
    suspend fun addFilm(filmItemMini: FilmItemBig)

    @Query("DELETE FROM FilmItemBig WHERE filmId=(:id)")
    suspend fun removeFilm(id: Int)
}