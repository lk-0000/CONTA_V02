package com.zces.conta.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Maps to <billOfQuantities><section><subsection><position> in ZCES_CONTA_project.xsd.
 * [id] is the XML position's stable `id` attribute (e.g. "NAMA-PAS-B3-301-A") — CLAUDE.md
 * §Architecture: "Never identify a BOQ position only by description text."
 */
@Entity(
    tableName = "boq_positions",
    foreignKeys = [
        ForeignKey(
            entity = ProjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("projectId"), Index("projectId", "positionCode", "subpositionCode")],
)
data class BoqPositionEntity(
    @PrimaryKey val id: String,
    val projectId: String,
    val sectionCode: String,
    val sectionName: String?,
    val subsectionCode: String,
    val subsectionName: String?,
    val packageName: String?,      // <package> — e.g. "PASAZA", "NADSTRESEK"
    val positionCode: String,      // e.g. "3.01"
    val subpositionCode: String?,  // e.g. "a", "b"
    val description: String,
    val unit: String?,
    val contractQuantity: Double?,
    val unitPrice: Double?,
    val contractValue: Double?,
)
