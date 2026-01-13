package com.sid.kotlinassignentapp.ui.agentlist

import GetAgentsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AgentListViewModelFactory(
    private val getAgentsUseCase: GetAgentsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentListViewModel(getAgentsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}