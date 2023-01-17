package com.example.android.data.data_source.pref

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private val PREF_NUMBER = doublePreferencesKey("pref_number")

class PreferenceManager @Inject constructor(
    var context: Application
) {

    init {
        CoroutineScope(Dispatchers.IO).launch { setNumber(1.0) }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val getNumber: Flow<Double> = context.dataStore.data
        .map { preferences -> preferences[PREF_NUMBER] ?: 1.0 }

    suspend fun setNumber(number: Double) {
        context.dataStore.edit { settings -> settings[PREF_NUMBER] = number }
    }
}