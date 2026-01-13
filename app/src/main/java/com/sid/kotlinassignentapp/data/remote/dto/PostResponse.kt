package com.sid.kotlinassignentapp.data.remote.dto

data class PostResponse(
    val posts: List<PostDto>,
    val total: Int
)
