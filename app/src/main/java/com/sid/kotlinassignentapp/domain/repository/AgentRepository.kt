package com.sid.kotlinassignentapp.domain.repository

import androidx.paging.PagingData
import com.sid.kotlinassignentapp.domain.model.Agent
import com.sid.kotlinassignentapp.domain.model.Post
import com.sid.kotlinassignentapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface AgentRepository {

    suspend fun getAgents(limit: Int, skip: Int): Result<List<Agent>>

    suspend fun searchAgents(query: String): Result<List<Agent>>

    suspend fun getPostsByUser(userId: Int): Result<List<Post>>

    fun getPagedAgents(): Flow<PagingData<Agent>>

    fun searchPagedAgents(query: String): Flow<PagingData<Agent>>

}
