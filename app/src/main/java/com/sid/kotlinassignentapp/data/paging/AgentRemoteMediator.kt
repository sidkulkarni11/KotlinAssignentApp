package com.sid.kotlinassignentapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sid.kotlinassignentapp.data.local.appdatabase.AppDatabase
import com.sid.kotlinassignentapp.data.remote.api.AgentApi
import com.sid.kotlinassignentapp.domain.mapper.toDomain
import com.sid.kotlinassignentapp.domain.mapper.toEntity

@OptIn(ExperimentalPagingApi::class)
class AgentRemoteMediator(
    private val api: AgentApi,
    private val db: AppDatabase
) : RemoteMediator<Int, com.sid.kotlinassignentapp.data.local.entity.AgentEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, com.sid.kotlinassignentapp.data.local.entity.AgentEntity>
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.APPEND -> state.pages.size
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = api.getAgents(
                limit = state.config.pageSize,
                skip = page * state.config.pageSize
            )

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.agentDao().clearAll()
                }
                db.agentDao().insertAll(
                    response.users.map { it.toDomain().toEntity() }
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = response.users.isEmpty()
            )

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
