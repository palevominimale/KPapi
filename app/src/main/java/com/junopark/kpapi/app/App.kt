package com.junopark.kpapi.app

import android.app.Application
import com.junopark.kpapi.app.di.dataDi
import com.junopark.kpapi.app.di.domainDi
import com.junopark.kpapi.app.di.uiDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    uiDi,
                    dataDi,
                    domainDi
                )
            )
        }
    }
}