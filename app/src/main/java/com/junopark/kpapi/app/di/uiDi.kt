package com.junopark.kpapi.app.di

import com.junopark.kpapi.app.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {
    viewModel {
        MainViewModel(
            api = get(),
            db = get()
        )
    }
}