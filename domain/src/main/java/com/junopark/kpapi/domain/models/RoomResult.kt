package com.junopark.kpapi.domain.models

sealed interface RoomResult {
    object Empty:RoomResult
    data class Success(val data: Any) : RoomResult
    data class Error(val e: Throwable? = null): RoomResult
}