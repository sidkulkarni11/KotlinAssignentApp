package com.sid.kotlinassignentapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid.kotlinassignentapp.data.local.preferences.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val prefs: SettingsPreferences
) : ViewModel() {

    val offlineOnly = prefs.offlineOnly
    val autoRefresh = prefs.autoRefresh
    val lastRefresh = prefs.lastRefresh

    fun toggleOfflineOnly(enabled: Boolean) {
        viewModelScope.launch {
            prefs.setOfflineOnly(enabled)
        }
    }

    fun toggleAutoRefresh(enabled: Boolean) {
        viewModelScope.launch {
            prefs.setAutoRefresh(enabled)
        }
    }
}
