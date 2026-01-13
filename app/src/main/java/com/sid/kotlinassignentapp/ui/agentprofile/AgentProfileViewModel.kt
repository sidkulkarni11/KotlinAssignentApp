package com.sid.kotlinassignentapp.ui.agentprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sid.kotlinassignentapp.domain.model.Post
import com.sid.kotlinassignentapp.domain.usecase.GetPostsByUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AgentProfileViewModel(
    private val getPostsByUserUseCase: GetPostsByUserUseCase,
    private val userId: Int
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            val result = getPostsByUserUseCase(userId)
            if (result is com.sid.kotlinassignentapp.utils.Result.Success) {
                _posts.value = result.data
            }
        }
    }
}
