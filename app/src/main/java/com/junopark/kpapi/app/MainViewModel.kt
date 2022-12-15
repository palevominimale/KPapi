package com.junopark.kpapi.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junopark.kpapi.app.states.UiIntent
import com.junopark.kpapi.app.states.UiState
import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.domain.usecases.ApiTestUseCase
import com.junopark.kpapi.domain.usecases.GetTop250FilmsUseCase
import com.junopark.kpapi.domain.usecases.RoomUseCase
import com.junopark.kpapi.entities.ListResponse
import com.junopark.kpapi.entities.facts.FactsResponse
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.films.FilmItemMini
import com.junopark.kpapi.entities.filter.FilmFilter
import com.junopark.kpapi.entities.filter.FilterResponse
import com.junopark.kpapi.entities.seasons.SeasonsResponse
import com.junopark.kpapi.entities.similar.SimilarResponse
import com.junopark.kpapi.entities.top.TopResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.getScopeId

private const val TAG = "MVM"

class MainViewModel(
    private val api: ApiRepo,
    private val get250: GetTop250FilmsUseCase,
    private val test: ApiTestUseCase,
    private val db: RoomUseCase
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private val _uiState = MutableStateFlow<UiState>(UiState.Error.NoInternet)
    val uiState : StateFlow<UiState> get() = _uiState
    private var filter: FilmFilter = FilmFilter()

    init {
        processApi()
    }

    private fun processApi() {
        viewModelScope.launch {
            api.state.collect {
                when(it) {
                    is ApiResult.ApiSuccess -> {
                        when(it.data) {
                            is ListResponse -> {
                                Log.w(TAG, it.toString())
                                _uiState.emit(UiState.Ready.List(it.data, filter))
                            }
                            is FilmItemBig -> {
                                Log.w(TAG, it.toString())
                                _uiState.emit(UiState.Ready.Single(it.data, filter))
                            }
                            is SeasonsResponse -> {
                                Log.w(TAG, it.toString())
                                _uiState.emit(UiState.Ready.List(it.data, filter))
                            }
                            is SimilarResponse -> {
                                Log.w(TAG, it.toString())
                                _uiState.emit(UiState.Ready.List(it.data, filter))
                            }
                            is FactsResponse -> {
                                Log.w(TAG, it.toString())
                            }
                            is FilterResponse -> {
                                Log.w(TAG, it.toString())
                            }
                            is TopResponse -> {
                                _uiState.emit(UiState.Ready.List(it.data, filter))
                                Log.w(TAG, it.toString())
                            }
                            else -> {
                                Log.w(TAG, "unexpected data: ${it.toString()}")
                            }
                        }
                    }
                    is ApiResult.ApiError -> {
                        _uiState.emit(
                            UiState.Error.HttpError(
                            code = it.code ?: 0,
                            message = it.message ?: "")
                        )
                    }
                    is ApiResult.ApiException -> {
                        _uiState.emit(UiState.Error.Exception(it.e ?: Throwable()))
                    }
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
                    _uiState.emit(UiState.Ready.List(db.getFilms()))
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
                is UiIntent.Search.ByKeyword -> {
                    _uiState.emit(UiState.IsLoading)
                    api.getByKeywordSearch(
                        query = filter.keyword
                    )
                }
                is UiIntent.Search.ByName -> {
                    _uiState.emit(UiState.IsLoading)
                    api.getByKeywordSearch(
                        query = filter.keyword
                    )
                }
                is UiIntent.Search.Relevant -> {
                    _uiState.emit(UiState.IsLoading)
                    api.getSimilar(intent.id)
                }

                is UiIntent.Favorites.Add -> db.addFilm(intent.item)
                is UiIntent.Favorites.Remove -> db.removeFilm(intent.item)
            }
        }
    }

    fun getFilms() {
        viewModelScope.launch{
            db.getFilms()
        }
    }

    fun getFilm(id: Int) {
        viewModelScope.launch{
            db.getFilm(id)
        }
    }

    fun addFilm(filmItemMini: FilmItemMini) {
        viewModelScope.launch{
            db.addFilm(filmItemMini)
        }
    }

    fun removeFilm(filmItemMini: FilmItemMini) {
        viewModelScope.launch{
            db.removeFilm(filmItemMini)
        }
    }
}