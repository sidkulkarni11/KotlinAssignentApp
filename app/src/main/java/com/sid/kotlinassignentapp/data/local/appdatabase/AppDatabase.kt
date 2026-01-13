package com.sid.kotlinassignentapp.data.local.appdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sid.kotlinassignentapp.data.local.dao.AgentDao
import com.sid.kotlinassignentapp.data.local.entity.AgentEntity

@Database(
    entities = [AgentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun agentDao(): AgentDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agent_db"
                ).build().also { INSTANCE = it }
            }
    }
}
