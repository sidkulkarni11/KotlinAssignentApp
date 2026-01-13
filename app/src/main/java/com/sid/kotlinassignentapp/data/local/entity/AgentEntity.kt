package com.sid.kotlinassignentapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agents")
data class AgentEntity(
    @PrimaryKey val id: Int,
    val fullName: String,
    val avatarUrl: String
)