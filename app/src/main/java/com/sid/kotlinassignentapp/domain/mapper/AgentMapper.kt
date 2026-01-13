package com.sid.kotlinassignentapp.domain.mapper

import com.sid.kotlinassignentapp.data.remote.dto.UserDto
import com.sid.kotlinassignentapp.domain.model.Agent
import com.sid.kotlinassignentapp.ui.agentlist.AgentUiModel

// DTO → Domain
fun UserDto.toDomain(): Agent {
    return Agent(
        id = id,
        fullName = "$firstName $lastName",
        avatarUrl = image
    )
}

// Domain → UI
fun Agent.toUiModel(): AgentUiModel {
    return AgentUiModel(
        id = id,
        name = fullName,
        avatar = avatarUrl
    )
}
