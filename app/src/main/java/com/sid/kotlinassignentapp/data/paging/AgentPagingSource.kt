package com.sid.kotlinassignentapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sid.kotlinassignentapp.data.remote.api.AgentApi
import com.sid.kotlinassignentapp.domain.mapper.toDomain
import com.sid.kotlinassignentapp.domain.model.Agent

class AgentSearchPagingSource(
    private val api: AgentApi,
    private val query: String
) : PagingSource<Int, Agent>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Agent> {
        return try {
            val response = api.searchAgents(query)
            LoadResult.Page(
                data = response.users.map { it.toDomain() },
                prevKey = null,
                nextKey = null // search endpoint is not paginated
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Agent>): Int? = null
}
