package com.junopark.kpapi.app.states

import androidx.paging.PagingData
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.films.FilmItemMini
import com.junopark.kpapi.entities.prefs.PrefsDTO
import kotlinx.coroutines.flow.Flow

sealed interface UiState {
    object Splash : UiState
    object IsLoading : UiState
    sealed interface Ready {
        object Empty : UiState
        data class FilmList(
            val data: List<FilmItemBig>,
            val prefs: PrefsDTO
        ) : UiState
        data class FilmListFlow (
            val data: Flow<PagingData<FilmItemBig>>,
            val prefs: PrefsDTO
        ) : UiState
        data class Single(
            val data: FilmItemBig,
            val prefs: PrefsDTO
        ) : UiState
        data class Favorites(
            val data: FilmItemMini,
            val prefs: PrefsDTO
        ) : UiState
    }
    sealed interface Error {
        object NoInternet : UiState
        data class HttpError(val code: Int, val message: String) : UiState
        data class Exception(val e: Throwable) : UiState
    }
}