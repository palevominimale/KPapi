package com.junopark.kpapi.data.api.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.junopark.kpapi.entities.films.FilmItemMini

@Dao
interface FavsFilmsDAO {

    @Query("SELECT * FROM filmItemMini")
    suspend fun getFilms(): List<FilmItemMini>

    @Query("SELECT * FROM FilmItemMini WHERE filmId=(:id)")
    suspend fun getFilm(id: Int?): FilmItemMini

    @Insert
    suspend fun addFilm(filmItemMini: FilmItemMini)

    @Delete
    suspend fun removeFilm(filmItemMini: FilmItemMini)
}