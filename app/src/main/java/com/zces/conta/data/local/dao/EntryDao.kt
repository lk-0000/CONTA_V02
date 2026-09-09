package com.zces.conta.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.zces.conta.data.local.entity.EntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {

    // VSE — full project timeline, newest first
    @Query("SELECT * FROM entries WHERE projectId = :projectId ORDER BY entryDateTime DESC")
    fun observeAllForProject(projectId: String): Flow<List<EntryEntity>>

    // DANES — today only
    @Query("SELECT * FROM entries WHERE projectId = :projectId AND entryDate = :isoDate ORDER BY entryDateTime DESC")
    fun observeForDay(projectId: String, isoDate: String): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE projectId = :projectId AND type = :type ORDER BY entryDateTime DESC")
    fun observeByType(projectId: String, type: String): Flow<List<EntryEntity>>

    @Query("SELECT * FROM entries WHERE boqPositionId = :boqPositionId ORDER BY entryDateTime DESC")
    fun observeForBoqPosition(boqPositionId: String): Flow<List<EntryEntity>>

    // Gantt field progress: sum of executed_work quantity for a mapped BOQ position.
    @Query(
        """
        SELECT COALESCE(SUM(quantity), 0) FROM entries
        WHERE boqPositionId = :boqPositionId AND type = 'EXECUTED_WORK'
        """,
    )
    fun observeExecutedQuantitySum(boqPositionId: String): Flow<Double>

    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun getById(id: String): EntryEntity?

    @Query("SELECT * FROM entries WHERE id = :id")
    fun observeById(id: String): Flow<EntryEntity?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entry: EntryEntity)

    // Edits go through update(): same id, caller is responsible for bumping revision/updatedAt
    // and preserving createdAt (see EntryRepository.update).
    @Update
    suspend fun update(entry: EntryEntity)

    @Delete
    suspend fun delete(entry: EntryEntity)
}
