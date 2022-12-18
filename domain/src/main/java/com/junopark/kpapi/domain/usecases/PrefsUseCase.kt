package com.junopark.kpapi.domain.usecases

import com.junopark.kpapi.domain.interfaces.ApiRepo
import com.junopark.kpapi.domain.interfaces.PrefsRepo
import com.junopark.kpapi.entities.prefs.PrefsDTO

class PrefsUseCase(
    private val prefsApi: PrefsRepo,
    private val api: ApiRepo
) {

    suspend fun getPrefs() : PrefsDTO? {
        return if(!prefsApi.checkPrefs()) {
            api.getFilters()
            null
        } else {
            prefsApi.getPrefs()
        }
    }
    fun setPrefs(prefs: PrefsDTO) = prefsApi.setPrefs(prefs)
}