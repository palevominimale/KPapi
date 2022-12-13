package com.junopark.kpapi.app.di

import com.junopark.kpapi.domain.usecases.GetTop250FilmsUseCase
import org.koin.dsl.module

val domainDi = module {
    single {
        GetTop250FilmsUseCase(
            api = get()
        )
    }
}