package com.junopark.kpapi.app.states

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
    }
    sealed interface Filter {
        object Clear : UiIntent
        data class Set(val filter: FilmFilter) : UiIntent
    }
    sealed interface Favorites {
        data class Add(val item: FilmItemMini) : UiIntent
        data class Remove(val id: Int) : UiIntent
    }
}