package com.sid.kotlinassignentapp.data.local.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsPreferences(private val context: Context) {

    companion object {
        private val OFFLINE_ONLY = booleanPreferencesKey("offline_only")
        private val AUTO_REFRESH = booleanPreferencesKey("auto_refresh")
        private val LAST_REFRESH = longPreferencesKey("last_refresh")
    }

    val offlineOnly: Flow<Boolean> =
        context.dataStore.data.map { it[OFFLINE_ONLY] ?: false }

    val autoRefresh: Flow<Boolean> =
        context.dataStore.data.map { it[AUTO_REFRESH] ?: false }

    val lastRefresh: Flow<Long> =
        context.dataStore.data.map { it[LAST_REFRESH] ?: 0L }

    suspend fun setOfflineOnly(enabled: Boolean) {
        context.dataStore.edit { it[OFFLINE_ONLY] = enabled }
    }

    suspend fun setAutoRefresh(enabled: Boolean) {
        context.dataStore.edit { it[AUTO_REFRESH] = enabled }
    }

    suspend fun updateLastRefresh(time: Long) {
        context.dataStore.edit { it[LAST_REFRESH] = time }
    }
}
