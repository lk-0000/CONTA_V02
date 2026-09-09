package com.zces.conta.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zces.conta.data.local.entity.BoqPositionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoqPositionDao {

    @Query("SELECT * FROM boq_positions WHERE projectId = :projectId ORDER BY sectionCode, subsectionCode, positionCode, subpositionCode")
    fun observeForProject(projectId: String): Flow<List<BoqPositionEntity>>

    @Query(
        """
        SELECT * FROM boq_positions
        WHERE projectId = :projectId
        AND (positionCode LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY positionCode, subpositionCode
        """,
    )
    fun search(projectId: String, query: String): Flow<List<BoqPositionEntity>>

    @Query("SELECT * FROM boq_positions WHERE id = :id")
    suspend fun getById(id: String): BoqPositionEntity?

    // Import commit is transactional (see ProjectImportRepository); duplicate stable `id`
    // within one XML must be rejected as an explicit import error, not merged silently.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(positions: List<BoqPositionEntity>)
}
