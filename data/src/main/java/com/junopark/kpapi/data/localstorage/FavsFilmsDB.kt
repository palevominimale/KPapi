package com.junopark.kpapi.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.junopark.kpapi.entities.films.FilmItemMini
private const val DATABASE_NAME = "films.db"

@Database(entities = [FilmItemMini::class], version = 1)
abstract class FavsFilmsDB : RoomDatabase() {

    abstract fun favsFilmsDAO(): FavsFilmsDAO

    companion object {
        private var INSTANCE: FavsFilmsDB? = null
        fun getInstance(context: Context): FavsFilmsDB? {
            if(INSTANCE == null) synchronized(FavsFilmsDB::class) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    FavsFilmsDB::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}