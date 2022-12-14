package com.junopark.kpapi.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.models.ApiResult
import com.junopark.kpapi.domain.usecases.ApiTestUseCase
import com.junopark.kpapi.domain.usecases.GetTop250FilmsUseCase
import com.junopark.kpapi.domain.usecases.RoomUseCase
import com.junopark.kpapi.entities.ListResponse
import com.junopark.kpapi.entities.facts.FactsResponse
import com.junopark.kpapi.entities.films.FilmItemBig
import com.junopark.kpapi.entities.films.FilmItemMini
import com.junopark.kpapi.entities.filter.FilterResponse
import com.junopark.kpapi.entities.seasons.SeasonsResponse
import com.junopark.kpapi.entities.similar.SimilarResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MVM"

class MainViewModel(
    private val api: ApiRepo,
    private val get250: GetTop250FilmsUseCase,
    private val test: ApiTestUseCase,
    private val db:RoomUseCase
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        viewModelScope.launch {
            api.state.collect {
                when(it) {
                    is ApiResult.ApiSuccess -> {
                        when(it.data) {
                            is ListResponse -> {Log.w(TAG, it.toString())}
                            is FilmItemBig -> {Log.w(TAG, it.toString())}
                            is SeasonsResponse -> {Log.w(TAG, it.toString())}
                            is SimilarResponse -> {Log.w(TAG, it.toString())}
                            is FactsResponse -> {Log.w(TAG, it.toString())}
                            is FilterResponse -> {Log.w(TAG, it.toString())}
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    fun get250() {
        scope.launch {
            get250.invoke()
        }
    }

    fun testApi() {
        scope.launch {
            test.invoke()
        }
    }

    fun getFilms() {
        viewModelScope.launch{
            db.getFilms()
        }
    }

    fun getFilm(id: Int?) {
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