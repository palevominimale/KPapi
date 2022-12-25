package com.junopark.kpapi.data.localstorage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.junopark.kpapi.entities.common.CountryItem
import com.junopark.kpapi.entities.common.GenreItem
import java.lang.reflect.Type

class Converters {

    private var gson: Gson = Gson()
    @TypeConverter
    fun stringToCountryItemList(data: String) : ArrayList<CountryItem> {
        val listType: Type = object : TypeToken<ArrayList<CountryItem>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun countryItemListToString(list: ArrayList<CountryItem>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToGenreItemList(data: String) : ArrayList<GenreItem> {
        val listType: Type = object : TypeToken<ArrayList<GenreItem>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun genreItemListToString(list: ArrayList<GenreItem>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToListStrings(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listStringsToString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun countryItemToString(countryItem: CountryItem) : String {
        return gson.toJson(countryItem)
    }

    @TypeConverter
    fun stringToCountryItem(data: String?) : CountryItem    {
        if (data == null || data.isEmpty()) {
            return CountryItem()
        }
        val listType: Type = object : TypeToken<CountryItem>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun genreItemToString(genreItem: GenreItem) : String {
        return gson.toJson(genreItem)
    }

    @TypeConverter
    fun stringToGenreItem(data: String?) : GenreItem {
        if(data == null || data.isEmpty()) {
            return GenreItem()
        }
        val listType: Type = object : TypeToken<GenreItem>() {}.type
        return gson.fromJson(data, listType)
    }
}