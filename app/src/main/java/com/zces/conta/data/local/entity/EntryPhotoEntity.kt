package com.zces.conta.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Photo metadata only — the actual file lives under app-controlled storage at
 * [relativePath] (see PhotoStorage), never as a Room BLOB (CLAUDE.md §Architecture).
 * Every photo belongs to exactly one Entry, which fixes its project/date/context
 * (PROJECT_CONTEXT.md #3.11).
 */
@Entity(
    tableName = "entry_photos",
    foreignKeys = [
        ForeignKey(
            entity = EntryEntity::class,
            parentColumns = ["id"],
            childColumns = ["entryId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("entryId")],
)
data class EntryPhotoEntity(
    @PrimaryKey val id: String,
    val entryId: String,
    val relativePath: String,   // relative to the app's photos root; see PhotoStorage
    val sortOrder: Int,
    val capturedAt: Long,
    val source: PhotoSource,
)

enum class PhotoSource { CAMERA, GALLERY }
