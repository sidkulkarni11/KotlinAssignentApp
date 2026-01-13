package com.sid.kotlinassignentapp.ui.agentprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sid.kotlinassignentapp.domain.usecase.GetPostsByUserUseCase

class AgentProfileViewModelFactory(
    private val useCase: GetPostsByUserUseCase,
    private val userId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgentProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AgentProfileViewModel(useCase, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
