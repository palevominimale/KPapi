package com.junopark.kpapi.domain.interfaces

import com.junopark.kpapi.entities.prefs.PrefsDTO

interface PrefsRepo {

    fun setPrefs(prefs: PrefsDTO)
    fun getPrefs() : PrefsDTO

}