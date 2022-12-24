package com.junopark.kpapi.app.states

import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.films.FilmItemMini
import com.junopark.kpapi.entities.filter.FilmFilter

sealed interface UiIntent {
    sealed interface Show {
        object Favorites: UiIntent
        object Shared: UiIntent
        object Top: UiIntent
    }
    sealed interface Search {
        object ByFilter : UiIntent
        data class ByKeyword(val query: String) : UiIntent
        data class ByName(val query: String) : UiIntent
        data class Relevant(val id: Int) : UiIntent
        data class ById (val id: Int) : UiIntent
    }
    sealed interface Filter {
        object Clear : UiIntent
        data class Set(val filter: FilmFilter) : UiIntent
    }
    sealed interface Favorites {
        object GetFilms : UiIntent
        data class GetFilm(val id: Int) : UiIntent
        data class Add(val item: FilmItemBig) : UiIntent
        data class Remove(val id: Int) : UiIntent
    }
    sealed interface Navigate {
        object Back : UiIntent
    }
}