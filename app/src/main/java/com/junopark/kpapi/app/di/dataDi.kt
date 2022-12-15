package com.junopark.kpapi.app.di

import com.junopark.kpapi.data.repos.ApiRepoImpl
import com.junopark.kpapi.data.repos.FavsFilmsDBRepoImpl
import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.interfaces.FavsFilmsDBRepo
import com.junopark.kpapi.domain.usecases.RoomUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataDi = module {
    single <ApiRepo> {
        ApiRepoImpl()
    }

    single {
        RoomUseCase(
            favsFilms = get()
        )
    }

    single <FavsFilmsDBRepo> {
        FavsFilmsDBRepoImpl(
            context = androidContext()
        )
    }
}