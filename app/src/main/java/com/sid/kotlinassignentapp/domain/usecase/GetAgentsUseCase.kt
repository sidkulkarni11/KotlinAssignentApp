import com.sid.kotlinassignentapp.domain.repository.AgentRepository

class GetAgentsUseCase(
    private val repository: AgentRepository
) {
    operator fun invoke() = repository.getPagedAgents()

    fun search(query: String) = repository.searchPagedAgents(query)
}
