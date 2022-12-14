package com.junopark.kpapi.app.di

import com.junopark.kpapi.data.repos.ApiRepoImpl
import com.junopark.kpapi.domain.interfaces.ApiRepo
import org.koin.dsl.module

val dataDi = module {
    single <ApiRepo> {
        ApiRepoImpl()
    }
}