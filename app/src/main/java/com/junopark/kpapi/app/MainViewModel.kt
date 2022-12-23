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
import com.junopark.kpapi.entities.common.GenreItem
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
    private var currentPrefs: PrefsDTO = PrefsDTO()
    val uiState : StateFlow<UiState> get() = _uiState

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
                    is ApiResult.ApiSuccess.FilmList -> _uiState.emit(UiState.Ready.FilmListFlow(api.listFlow, currentPrefs))
                    is ApiResult.ApiSuccess.SingleFilm -> _uiState.emit(UiState.Ready.Single(it.item, currentPrefs))
                    is ApiResult.ApiSuccess.Empty -> _uiState.emit(UiState.Ready.Empty)
                    is ApiResult.ApiSuccess.FiltersList -> prefs.setPrefs(PrefsDTO(it.item.genres, it.item.countries))
                    is ApiResult.ApiError -> _uiState.emit(UiState.Error.HttpError(code = it.code ?: 0,message = it.message ?: ""))
                    is ApiResult.ApiException -> {
                        _uiState.emit(UiState.Error.Exception(it.e ?: Throwable()))
                        Log.e(TAG, "${it.e?.stackTraceToString()}")
                    }
                    else -> {}
                }
            }
        }
    }

    fun reduce(intent: UiIntent) {
        Log.w(TAG, intent.toString())
        scope.launch {
            when(intent) {
                is UiIntent.Filter.Clear -> currentPrefs = currentPrefs.copy(filter = FilmFilter())
                is UiIntent.Filter.Set -> currentPrefs = currentPrefs.copy(filter = intent.filter)

                is UiIntent.Show.Favorites -> {
                    previousState = _uiState.value
                    _uiState.emit(UiState.IsLoading)
//                    _uiState.emit(UiState.Ready.FilmList(db.getFilms()))
                }
                is UiIntent.Show.Shared -> {
                    previousState = _uiState.value
                    _uiState.emit(UiState.IsLoading)
                }
                is UiIntent.Show.Top -> {
                    previousState = _uiState.value
                    _uiState.emit(UiState.IsLoading)
                    api.getTop()
                }

                is UiIntent.Search.ByFilter -> {
                    previousState = _uiState.value
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
                    previousState = _uiState.value
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
                    previousState = _uiState.value
                    _uiState.emit(UiState.IsLoading)
                    api.getByKeywordSearch(query = intent.query)
                }
                is UiIntent.Search.Relevant -> {
                    previousState = _uiState.value
                    _uiState.emit(UiState.IsLoading)
                    api.getSimilar(intent.id)
                }
                is UiIntent.Search.ById -> {
                    previousState = _uiState.value
                    _uiState.emit(UiState.IsLoading)
                    api.getById(intent.id)
                }

                is UiIntent.Favorites.GetFilms -> db.getFilms()
                is UiIntent.Favorites.GetFilm -> db.getFilm(intent.id)
                is UiIntent.Favorites.Add -> db.addFilm(intent.item)
                is UiIntent.Favorites.Remove -> db.removeFilm(intent.id)

                is UiIntent.Navigate.Back -> if(uiState.value != previousState) _uiState.emit(previousState)
            }
        }
    }
}