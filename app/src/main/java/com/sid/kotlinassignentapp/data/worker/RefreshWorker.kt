package com.sid.kotlinassignentapp.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sid.kotlinassignentapp.data.local.preferences.SettingsPreferences

class RefreshWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val prefs = SettingsPreferences(applicationContext)

        // Update timestamp only (network handled via Paging)
        prefs.updateLastRefresh(System.currentTimeMillis())

        return Result.success()
    }
}
