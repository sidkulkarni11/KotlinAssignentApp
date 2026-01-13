package com.sid.kotlinassignentapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sid.kotlinassignentapp.data.local.entity.AgentEntity

@Dao
interface AgentDao {

    @Query("SELECT * FROM agents ORDER BY fullName ASC")
    fun pagingSource(): PagingSource<Int, AgentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(agents: List<AgentEntity>)

    @Query("DELETE FROM agents")
    suspend fun clearAll()
}
