package com.sid.kotlinassignentapp.domain.usecase

import com.sid.kotlinassignentapp.domain.repository.AgentRepository

class SearchAgentsUseCase(
    private val repository: AgentRepository
) {
    suspend operator fun invoke(query: String) =
        repository.searchAgents(query)
}
