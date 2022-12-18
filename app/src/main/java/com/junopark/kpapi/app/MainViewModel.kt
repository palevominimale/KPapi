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
    val uiState : StateFlow<UiState> get() = _uiState
    private var filter: FilmFilter = FilmFilter()

    init {
        processApi()
    }

    private fun processApi() {
        scope.launch {
            Log.w(TAG, "${prefs.getPrefs()}")
        }
        viewModelScope.launch {
            api.state.collect {
                Log.w(TAG, "$it")
                when(it) {
                    is ApiResult.ApiSuccess.FilmList -> _uiState.emit(UiState.Ready.FilmList(it.items,filter))
                    is ApiResult.ApiSuccess.SingleFilm -> _uiState.emit(UiState.Ready.Single(it, filter))
                    is ApiResult.ApiSuccess.Empty -> _uiState.emit(UiState.Ready.Empty)
                    is ApiResult.ApiSuccess.FiltersList -> prefs.setPrefs(PrefsDTO(it.item.genres, it.item.countries, filter))

                    is ApiResult.ApiError -> _uiState.emit(UiState.Error.HttpError(code = it.code ?: 0,message = it.message ?: ""))
                    is ApiResult.ApiException -> _uiState.emit(UiState.Error.Exception(it.e ?: Throwable()))
                    else -> {}
                }
            }
        }
    }

    fun reduce(intent: UiIntent) {
        scope.launch {
            when(intent) {
                is UiIntent.Filter.Clear -> filter = FilmFilter()
                is UiIntent.Filter.Set -> filter = intent.filter

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
                    if(filter.genres == null) {
                        api.getByFilter(
                            order = filter.order,
                            type = filter.type,
                            ratingFrom = filter.ratingFrom,
                            ratingTo = filter.ratingTo,
                            yearFrom = filter.yearFrom,
                            yearTo = filter.yearTo,
                        )
                    } else {
                        api.getByFilter(
                            countries = filter.countries,
                            genres =  filter.genres,
                            order = filter.order,
                            type = filter.type,
                            ratingFrom = filter.ratingFrom,
                            ratingTo = filter.ratingTo,
                            yearFrom = filter.yearFrom,
                            yearTo = filter.yearTo,
                            imdbId = filter.imdbId,
                            keyword = filter.keyword,
                        )
                    }

                }
                is UiIntent.Search.ByKeyword -> {
                    _uiState.emit(UiState.IsLoading)
                    if(filter.keyword != null) {
                        api.getByKeywordSearch(
                            query = filter.keyword!!
                        )
                    } else {
                        _uiState.emit(UiState.Ready.Empty)
                    }

                }
                is UiIntent.Search.ByName -> {
                    _uiState.emit(UiState.IsLoading)
                    if(filter.keyword != null) {
                        api.getByKeywordSearch(
                            query = filter.keyword!!
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
            }
        }
    }
}