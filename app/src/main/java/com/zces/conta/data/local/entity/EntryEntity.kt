package com.zces.conta.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * The core field-capture record. One row per Entry regardless of [type]; type-specific
 * fields that don't apply to a given [type] stay null rather than being guessed
 * (PROJECT_CONTEXT.md #6: "Missing data is never guessed").
 *
 * Editing (CLAUDE.md §Product guardrails, Manual §9): editing updates this same row,
 * increments [revision], sets [updatedAt] = now, and never touches [createdAt].
 */
@Entity(
    tableName = "entries",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = BoqPositionEntity::class,
            parentColumns = ["id"],
            childColumns = ["boqPositionId"],
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = ScheduleTaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["scheduleTaskId"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [
        Index("projectId"),
        Index("boqPositionId"),
        Index("scheduleTaskId"),
        Index("projectId", "entryDate"),
        Index("type"),
    ],
)
data class EntryEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val type: EntryType,

    // Applies only when type.isBoqLinked == true; must belong to the same projectId.
    val boqPositionId: String?,

    // Optional link used by "delay" entries and by any entry the field manager ties to a
    // Gantt task; must belong to the same projectId.
    val scheduleTaskId: String?,

    val entryDate: String,      // ISO-8601 date the entry logically belongs to (editable, defaults to today)
    val entryDateTime: Long,    // full timestamp used for DANES/VSE chronological ordering

    val quantity: Double?,      // e.g. executed_work "Evidentirano" quantity; never contract-confirmed
    val unit: String?,          // auto-filled from BOQ position when boqPositionId is set
    val location: String?,
    val description: String?,
    val workersOwn: Int?,       // workforce entry
    val workersSub: Int?,       // workforce entry
    val weatherPeriod: String?,     // weather entry: jutro/dopoldne/popoldne/celodnevno/custom
    val weatherCondition: String?,
    val temperatureC: Double?,      // optional
    val delayReason: String?,       // delay entry
    val delayStartTime: Long?,
    val delayEndTime: Long?,
    val delayAffectedWorkers: Int?,
    val delayHours: Double?,        // calculated, stored for display/export stability
    val delayLostManHours: Double?, // calculated, stored for display/export stability
    val additionalWorkStatus: String? = "evidentirano", // additional_work: never auto-merged into contract quantity

    val authorName: String,     // local author profile name at time of creation (DataStore-sourced)
    val createdAt: Long,
    val updatedAt: Long,
    val revision: Int = 1,
)
