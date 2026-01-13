package com.sid.kotlinassignentapp.ui.agentlist

import GetAgentsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.sid.kotlinassignentapp.domain.mapper.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class AgentListViewModel(
    private val getAgentsUseCase: GetAgentsUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    val agents: Flow<PagingData<AgentUiModel>> =
        searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    getAgentsUseCase()
                } else {
                    getAgentsUseCase.search(query)
                }
            }
            .map { pagingData ->
                pagingData.map { it.toUiModel() }
            }
            .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }
}