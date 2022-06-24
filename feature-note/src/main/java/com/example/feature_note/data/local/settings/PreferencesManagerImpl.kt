package com.example.feature_note.data.local.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.feature_note.data.local.settings.model.UserSettings
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

enum class SortType {
    ASCENDING, DESCENDING
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class PreferencesManagerImpl(private val context: Context) : PreferencesManager {

    private val preferencesFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortType = SortType.valueOf(
                preferences[SORT_TYPE] ?: SortType.ASCENDING.name
            )
            UserSettings(sortType = sortType)
        }

    override suspend fun saveSortType(sortType: SortType) {
        context.dataStore.edit { settings ->
            settings[SORT_TYPE] = sortType.name
        }
    }

    override fun getUserSettings() = preferencesFlow

    companion object {
        val SORT_TYPE = stringPreferencesKey("sort_order")
    }
}
