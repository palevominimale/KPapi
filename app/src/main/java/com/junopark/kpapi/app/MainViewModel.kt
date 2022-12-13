package com.junopark.kpapi.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.usecases.GetTop250FilmsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MVM"

class MainViewModel(
    private val api: ApiRepo,
    private val get250: GetTop250FilmsUseCase
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        viewModelScope.launch {
            api.state.collect {
                Log.w(TAG, it.toString())
            }
        }
    }

    fun get250() {
        scope.launch {
            get250.invoke()
        }
    }

}