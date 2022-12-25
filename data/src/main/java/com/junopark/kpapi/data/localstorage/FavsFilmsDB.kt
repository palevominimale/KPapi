package com.junopark.kpapi.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.junopark.kpapi.entities.films.FilmItemBig

private const val DATABASE_NAME = "films.db"

@Database(entities = [FilmItemBig::class], version = 1)
@TypeConverters(Converters::class)

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