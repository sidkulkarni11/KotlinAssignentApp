package com.sid.kotlinassignentapp.domain.mapper

import com.sid.kotlinassignentapp.data.local.entity.AgentEntity
import com.sid.kotlinassignentapp.domain.model.Agent

fun AgentEntity.toDomain(): Agent =
    Agent(id, fullName, avatarUrl)

fun Agent.toEntity(): AgentEntity =
    AgentEntity(id, fullName, avatarUrl)
