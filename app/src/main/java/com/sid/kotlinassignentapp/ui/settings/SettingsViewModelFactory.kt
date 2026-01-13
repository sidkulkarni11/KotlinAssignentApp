package com.sid.kotlinassignentapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid.kotlinassignentapp.data.local.preferences.SettingsPreferences

class SettingsViewModelFactory(
    private val prefs: SettingsPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(prefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
