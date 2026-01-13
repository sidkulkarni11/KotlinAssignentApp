package com.sid.kotlinassignentapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sid.kotlinassignentapp.data.local.appdatabase.AppDatabase
import com.sid.kotlinassignentapp.data.paging.AgentRemoteMediator
import com.sid.kotlinassignentapp.data.remote.api.AgentApi
import com.sid.kotlinassignentapp.domain.mapper.toDomain
import com.sid.kotlinassignentapp.domain.model.Agent
import com.sid.kotlinassignentapp.domain.model.Post
import com.sid.kotlinassignentapp.domain.repository.AgentRepository
import com.sid.kotlinassignentapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AgentRepositoryImpl(
    private val api: AgentApi,
    private val database: AppDatabase
) : AgentRepository {

    override suspend fun getAgents(limit: Int, skip: Int): Result<List<Agent>> {
        return safeApiCall {
            val response = api.getAgents(limit, skip)
            response.users.map { it.toDomain() }
        }
    }

    override suspend fun searchAgents(query: String): Result<List<Agent>> {
        return safeApiCall {
            val response = api.searchAgents(query)
            response.users.map { it.toDomain() }
        }
    }

    override suspend fun getPostsByUser(userId: Int): Result<List<Post>> {
        return safeApiCall {
            val response = api.getPostsByUser(userId)
            response.posts.map { it.toDomain() }
        }
    }

    /**
     * OFFLINE-FIRST PAGING
     * Room is source of truth
     * Network updates cache via RemoteMediator
     */
    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    override fun getPagedAgents(): Flow<PagingData<Agent>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = AgentRemoteMediator(api, database),
            pagingSourceFactory = { database.agentDao().pagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun searchPagedAgents(query: String): Flow<PagingData<Agent>> {
        // Search is network-based only (DummyJSON search not paged)
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                com.sid.kotlinassignentapp.data.paging.AgentSearchPagingSource(api, query)
            }
        ).flow
    }

    private suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(block())
            } catch (t: Throwable) {
                Result.Error(
                    message = t.message ?: "Something went wrong",
                    throwable = t
                )
            }
        }
    }
}
