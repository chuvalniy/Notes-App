package com.example.feature_note.data.local.settings

import com.example.feature_note.data.local.settings.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface PreferencesManager {

    suspend fun saveSortType(sortType: SortType)

    fun getUserSettings(): Flow<UserSettings>
}