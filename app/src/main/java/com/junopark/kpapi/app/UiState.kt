package com.junopark.kpapi.app

import com.junopark.kpapi.entities.films.FilmItemMini

sealed interface UiState {
    object IsLoading : UiState
    object NoInternet : UiState
    sealed interface Ready {
        object Empty : UiState
        data class List(val data: Any) : UiState
        data class Single(val data: Any) : UiState
        data class Favorites(val data: FilmItemMini) : UiState
    }
    sealed interface Error {
        data class HttpError(val code: Int, val message: String)
        data class Exception(val e: Throwable)
    }
}