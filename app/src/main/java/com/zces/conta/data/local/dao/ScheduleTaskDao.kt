package com.zces.conta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zces.conta.data.local.entity.ScheduleTaskEntity
import com.zces.conta.data.local.entity.ScheduleTaskPredecessorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleTaskDao {

    @Query("SELECT * FROM schedule_tasks WHERE projectId = :projectId ORDER BY start")
    fun observeForProject(projectId: String): Flow<List<ScheduleTaskEntity>>

    @Query("SELECT * FROM schedule_tasks WHERE boqPositionId = :boqPositionId")
    fun observeForBoqPosition(boqPositionId: String): Flow<List<ScheduleTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(tasks: List<ScheduleTaskEntity>)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPredecessors(predecessors: List<ScheduleTaskPredecessorEntity>)

    @Query("SELECT predecessorTaskId FROM schedule_task_predecessors WHERE taskId = :taskId")
    suspend fun getPredecessorIds(taskId: String): List<String>
}
