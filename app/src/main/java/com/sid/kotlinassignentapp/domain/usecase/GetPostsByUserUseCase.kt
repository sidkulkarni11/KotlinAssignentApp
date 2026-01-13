package com.sid.kotlinassignentapp.domain.usecase

import com.sid.kotlinassignentapp.domain.repository.AgentRepository

class GetPostsByUserUseCase(
    private val repository: AgentRepository
) {
    suspend operator fun invoke(userId: Int) =
        repository.getPostsByUser(userId)
}
