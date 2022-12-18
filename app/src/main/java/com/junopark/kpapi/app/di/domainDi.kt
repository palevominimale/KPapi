package com.junopark.kpapi.app.di

import com.junopark.kpapi.domain.usecases.ApiTestUseCase
import com.junopark.kpapi.domain.usecases.GetTop250FilmsUseCase
import com.junopark.kpapi.domain.usecases.PrefsUseCase
import org.koin.dsl.module

val domainDi = module {
    single {
        GetTop250FilmsUseCase(
            api = get()
        )
    }

    single {
        ApiTestUseCase(
            api = get()
        )
    }

    single {
        PrefsUseCase(
            prefsApi = get(),
            api = get()
        )
    }
}