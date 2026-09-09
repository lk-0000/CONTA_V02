package com.zces.conta.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zces.conta.data.local.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    @Query("SELECT * FROM projects WHERE archived = 0 ORDER BY updatedAt DESC")
    fun observeActiveProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE archived = 1 ORDER BY updatedAt DESC")
    fun observeArchivedProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun getById(projectId: String): ProjectEntity?

    @Query("SELECT * FROM projects WHERE id = :projectId")
    fun observeById(projectId: String): Flow<ProjectEntity?>

    // Duplicate stable IDs on XML import must surface as an explicit warning, never silently
    // overwrite (PROJECT_CONTEXT.md #6) — callers check getById() before inserting.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(project: ProjectEntity)

    @Update
    suspend fun update(project: ProjectEntity)

    @Delete
    suspend fun delete(project: ProjectEntity)

    @Query("UPDATE projects SET archived = :archived, updatedAt = :now WHERE id = :projectId")
    suspend fun setArchived(projectId: String, archived: Boolean, now: Long)
}
