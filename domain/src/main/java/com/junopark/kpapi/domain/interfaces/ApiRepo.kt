package com.junopark.kpapi.domain.interfaces

import com.junopark.kpapi.domain.models.ApiResult
import kotlinx.coroutines.flow.StateFlow

interface ApiRepo {

    val state : StateFlow<ApiResult>

    suspend fun getTop(type: String, page: Int)

}