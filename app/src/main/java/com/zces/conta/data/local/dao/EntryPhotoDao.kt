package com.zces.conta.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zces.conta.data.local.entity.EntryPhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryPhotoDao {

    @Query("SELECT * FROM entry_photos WHERE entryId = :entryId ORDER BY sortOrder")
    fun observeForEntry(entryId: String): Flow<List<EntryPhotoEntity>>

    @Query("SELECT * FROM entry_photos WHERE entryId = :entryId ORDER BY sortOrder")
    suspend fun getForEntry(entryId: String): List<EntryPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(photo: EntryPhotoEntity)

    @Delete
    suspend fun delete(photo: EntryPhotoEntity)

    // Used when a photo is removed from a not-yet-saved entry's preview list (Manual §7).
    @Query("DELETE FROM entry_photos WHERE id = :id")
    suspend fun deleteById(id: String)
}
