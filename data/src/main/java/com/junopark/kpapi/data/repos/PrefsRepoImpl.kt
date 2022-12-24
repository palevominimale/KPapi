package com.junopark.kpapi.data.repos

import android.content.Context
import com.google.gson.Gson
import com.junopark.kpapi.data.R
import com.junopark.kpapi.domain.interfaces.PrefsRepo
import com.junopark.kpapi.entities.prefs.PrefsDTO

class PrefsRepoImpl(
    context: Context
) : PrefsRepo {

    private val prefsName = context.getString(R.string.preferences_name)
    private val prefs = context.getSharedPreferences(prefsName,Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val gson = Gson()

    override fun setPrefs(prefs: PrefsDTO) {
        editor.putString(prefsName, gson.toJson(prefs)).commit()
    }
    override fun getPrefs(): PrefsDTO = gson.fromJson(prefs.getString(prefsName, ""), PrefsDTO::class.java)
    override fun checkPrefs() = prefs.getString(prefsName, "false") != "false"
}