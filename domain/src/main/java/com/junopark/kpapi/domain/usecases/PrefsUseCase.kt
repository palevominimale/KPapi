package com.junopark.kpapi.domain.usecases

import com.junopark.kpapi.domain.interfaces.PrefsRepo
import com.junopark.kpapi.entities.prefs.PrefsDTO

class PrefsUseCase(
    private val prefsApi: PrefsRepo
) {

    fun getPrefs() = prefsApi.getPrefs()
    fun setPrefs(prefs: PrefsDTO) = prefsApi.setPrefs(prefs)
}