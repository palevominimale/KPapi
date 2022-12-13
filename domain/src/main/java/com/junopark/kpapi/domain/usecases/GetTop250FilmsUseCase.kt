package com.junopark.kpapi.domain.usecases

import com.junopark.kpapi.domain.interfaces.ApiRepo

class GetTop250FilmsUseCase(
    private val api: ApiRepo
) {

    suspend fun invoke() = api.getTop(type = "TOP_250_BEST_FILMS", page = 1)

}