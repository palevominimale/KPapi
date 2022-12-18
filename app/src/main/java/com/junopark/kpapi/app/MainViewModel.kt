package com.junopark.kpapi.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.domain.usecases.PrefsUseCase
import com.junopark.kpapi.domain.usecases.RoomUseCase
import com.junopark.kpapi.entities.filter.FilmFilter
import com.junopark.kpapi.entities.prefs.PrefsDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "MVM"

class MainViewModel(
    private val api: ApiRepo,
    private val db: RoomUseCase,
    private val prefs: PrefsUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val _uiState = MutableStateFlow<UiState>(UiState.Error.NoInternet)
    private var previousState : UiState = UiState.Ready.Empty
    val uiState : StateFlow<UiState> get() = _uiState
    private var currentPrefs: PrefsDTO = PrefsDTO()

    init {
        processApi()
    }

    private fun processApi() {
        scope.launch {
            val currentPrefs = prefs.getPrefs()
            Log.w(TAG, "$currentPrefs")
            if(currentPrefs != null) this@MainViewModel.currentPrefs = currentPrefs
        }
        viewModelScope.launch {
            api.state.collect {
                Log.w(TAG, "$it")
                when(it) {
                    is ApiResult.ApiSuccess.FilmList -> {
                        previousState = _uiState.value
                        _uiState.emit(UiState.Ready.FilmList(it.items,currentPrefs))
                    }
                    is ApiResult.ApiSuccess.SingleFilm -> {
                        previousState = _uiState.value
                        _uiState.emit(UiState.Ready.Single(it, currentPrefs))
                    }
                    is ApiResult.ApiSuccess.Empty -> {
                        previousState = _uiState.value
                        _uiState.emit(UiState.Ready.Empty)
                    }
                    is ApiResult.ApiSuccess.FiltersList -> prefs.setPrefs(PrefsDTO(it.item.genres, it.item.countries))

                    is ApiResult.ApiError -> {
                        previousState = _uiState.value
                        _uiState.emit(UiState.Error.HttpError(code = it.code ?: 0,message = it.message ?: ""))
                    }
                    is ApiResult.ApiException -> {
                        previousState = _uiState.value
                        _uiState.emit(UiState.Error.Exception(it.e ?: Throwable()))
                    }
                    else -> {}
                }
            }
        }
    }

    fun reduce(intent: UiIntent) {
        scope.launch {
            when(intent) {
                is UiIntent.Filter.Clear -> currentPrefs = currentPrefs.copy(filter = FilmFilter())
                is UiIntent.Filter.Set -> currentPrefs = currentPrefs.copy(filter = intent.filter)

                is UiIntent.Show.Favorites -> {
                    _uiState.emit(UiState.IsLoading)
//                    _uiState.emit(UiState.Ready.FilmList(db.getFilms()))
                }
                is UiIntent.Show.Shared -> {
                    _uiState.emit(UiState.IsLoading)
                }
                is UiIntent.Show.Top -> {
                    _uiState.emit(UiState.IsLoading)
                    api.getTop()
                }

                is UiIntent.Search.ByFilter -> {
                    _uiState.emit(UiState.IsLoading)
                    if(currentPrefs.genres == null) {
                        api.getByFilter(
                            order = currentPrefs.filter?.order,
                            type = currentPrefs.filter?.type,
                            ratingFrom = currentPrefs.filter?.ratingFrom,
                            ratingTo = currentPrefs.filter?.ratingTo,
                            yearFrom = currentPrefs.filter?.yearFrom,
                            yearTo = currentPrefs.filter?.yearTo,
                        )
                    } else {
                        api.getByFilter(
                            countries = currentPrefs.filter?.countries,
                            genres =  currentPrefs.filter?.genres,
                            order = currentPrefs.filter?.order,
                            type = currentPrefs.filter?.type,
                            ratingFrom = currentPrefs.filter?.ratingFrom,
                            ratingTo = currentPrefs.filter?.ratingTo,
                            yearFrom = currentPrefs.filter?.yearFrom,
                            yearTo = currentPrefs.filter?.yearTo,
                            imdbId = currentPrefs.filter?.imdbId,
                            keyword = currentPrefs.filter?.keyword,
                        )
                    }

                }
                is UiIntent.Search.ByKeyword -> {
                    _uiState.emit(UiState.IsLoading)
                    if(currentPrefs.filter?.keyword != null) {
                        api.getByKeywordSearch(
                            query = currentPrefs.filter?.keyword!!
                        )
                    } else {
                        _uiState.emit(UiState.Ready.Empty)
                    }

                }
                is UiIntent.Search.ByName -> {
                    _uiState.emit(UiState.IsLoading)
                    if(currentPrefs.filter?.keyword != null) {
                        api.getByKeywordSearch(
                            query = currentPrefs.filter?.keyword!!
                        )
                    } else {
                        _uiState.emit(UiState.Ready.Empty)
                    }
                }
                is UiIntent.Search.Relevant -> {
                    _uiState.emit(UiState.IsLoading)
                    api.getSimilar(intent.id)
                }

                is UiIntent.Favorites.GetFilms -> db.getFilms()
                is UiIntent.Favorites.GetFilm -> db.getFilm(intent.id)
                is UiIntent.Favorites.Add -> db.addFilm(intent.item)
                is UiIntent.Favorites.Remove -> db.removeFilm(intent.id)

                is UiIntent.Navigate.Back -> { _uiState.emit(previousState) }
            }
        }
    }
}