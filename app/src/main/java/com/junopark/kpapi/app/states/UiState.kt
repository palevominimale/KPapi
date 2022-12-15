package com.junopark.kpapi.app.states

import com.junopark.kpapi.entities.films.FilmItemMini
import com.junopark.kpapi.entities.filter.FilmFilter

sealed interface UiState {
    object Splash : UiState
    object IsLoading : UiState
    sealed interface Ready {
        object Empty : UiState
        data class List(val data: Any, val filter: FilmFilter = FilmFilter()) : UiState
        data class Single(val data: Any, val filter: FilmFilter = FilmFilter()) : UiState
        data class Favorites(val data: FilmItemMini, val filter: FilmFilter = FilmFilter()) : UiState
    }
    sealed interface Error {
        object NoInternet : UiState
        data class HttpError(val code: Int, val message: String) : UiState
        data class Exception(val e: Throwable) : UiState
    }
}