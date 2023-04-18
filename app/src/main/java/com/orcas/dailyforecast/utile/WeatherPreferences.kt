package com.orcas.dailyforecast.utile

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.orcas.dailyforecast.utile.ConstantApp.PREFERENCE_CITY_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context : Context){
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getStoredTag(): String {
        return prefs.getString(PREFERENCE_CITY_ID, "")!!
    }
    fun setStoredTag(query: String) {
        prefs.edit().putString(PREFERENCE_CITY_ID, query).apply()
    }
}