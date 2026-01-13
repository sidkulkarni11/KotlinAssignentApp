package com.sid.kotlinassignentapp.data.remote.api

import com.sid.kotlinassignentapp.data.remote.dto.PostResponse
import com.sid.kotlinassignentapp.data.remote.dto.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AgentApi {

    @GET("users")
    suspend fun getAgents(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): UserResponse

    @GET("users/search")
    suspend fun searchAgents(
        @Query("q") query: String
    ): UserResponse

    @GET("posts/user/{id}")
    suspend fun getPostsByUser(
        @Path("id") userId: Int
    ): PostResponse
}
