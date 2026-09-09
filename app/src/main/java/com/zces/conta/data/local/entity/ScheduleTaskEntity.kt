package com.zces.conta.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Maps to <schedule><task> in ZCES_CONTA_project.xsd.
 * PROJECT_CONTEXT.md #3.15-17: Gantt is a read-only site-tracking view; V1 never rewrites
 * [start]/[finish] automatically because of delays.
 */
@Entity(
    tableName = "schedule_tasks",
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
    ],
    indices = [Index("projectId"), Index("boqPositionId")],
)
data class ScheduleTaskEntity(
    @PrimaryKey val id: String,       // XML <task id="..."> — stable ID
    val projectId: String,
    val wbs: String?,
    val name: String,
    val start: String,                // ISO-8601 date
    val finish: String,               // ISO-8601 date
    val durationDays: Double?,
    val milestone: Boolean = false,
    val boqPositionId: String?,       // <boqPosition> reference, must belong to same project
    val importedProgress: Double?,    // optional progress supplied by the source XML, distinct from field-evidenced progress
)

/**
 * Maps to <predecessors><predecessor> — a task can list zero or more predecessor task IDs.
 * Kept as a separate join table rather than a serialized column so predecessor IDs stay
 * queryable and referentially checkable against schedule_tasks.
 */
@Entity(
    tableName = "schedule_task_predecessors",
    primaryKeys = ["taskId", "predecessorTaskId"],
    foreignKeys = [
        ForeignKey(
            entity = ScheduleTaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("taskId")],
)
data class ScheduleTaskPredecessorEntity(
    val taskId: String,
    val predecessorTaskId: String,
)
