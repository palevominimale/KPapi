package com.junopark.kpapi.data.repos

import android.content.Context
import androidx.room.Room
import com.junopark.kpapi.data.localstorage.FavsFilmsDB
import com.junopark.kpapi.domain.interfaces.FavsFilmsDBRepo
import com.junopark.kpapi.domain.models.RoomResult
import com.junopark.kpapi.entities.films.FilmItemMini
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavsFilmsDBRepoImpl(context: Context) : FavsFilmsDBRepo {

    private val roomState = MutableStateFlow<RoomResult>(RoomResult.Success(FilmItemMini(null)))
    override val state: StateFlow<RoomResult> get() = roomState

    private val db: FavsFilmsDB = Room.databaseBuilder(
        context.applicationContext,
        FavsFilmsDB::class.java,
        "Films.db"
    ).build()

    private val filmsDAO = db.favsFilmsDAO()


    override suspend fun getFilms() {
        roomState.emit(try {
            val result = filmsDAO.getFilms()
            if(result != null) RoomResult.Success(result) else RoomResult.Empty
        } catch (e:Throwable){
            RoomResult.Error(e)
        })
    }

    override suspend fun getFilm(id: Int) {
        roomState.emit(try {
            val result = filmsDAO.getFilm(id)
            if(result != null) RoomResult.Success(result) else RoomResult.Empty
        } catch (e:Throwable){
            RoomResult.Error(e)
        })
    }

    override suspend fun addFilm(filmItemMini: FilmItemMini) {
        roomState.emit(try {
            val result = filmsDAO.addFilm(filmItemMini) as FilmItemMini
            if(result != null) RoomResult.Success(result) else RoomResult.Empty
        } catch (e:Throwable){
            RoomResult.Error(e)
        })
    }

    override suspend fun RemoveFilm(id: Int) {
        roomState.emit(try {
            val result = filmsDAO.removeFilm(id) as FilmItemMini
            if(result != null) RoomResult.Success(result) else RoomResult.Empty
        } catch (e:Throwable){
            RoomResult.Error(e)
        })
    }
}