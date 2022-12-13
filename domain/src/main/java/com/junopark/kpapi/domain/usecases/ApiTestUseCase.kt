package com.junopark.kpapi.domain.usecases

import com.junopark.kpapi.domain.interfaces.ApiRepo

class ApiTestUseCase(
    private val api: ApiRepo
) {

    suspend fun invoke() {
        api.getTop()
        api.getById()
        api.getSeasons()
        api.getSimilar()
        api.getFacts()
        api.getFilters()
    }

}