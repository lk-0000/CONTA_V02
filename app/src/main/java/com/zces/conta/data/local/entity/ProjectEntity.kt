package com.zces.conta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Maps to <projectInfo> in ZCES_CONTA_project.xsd, plus local-only bookkeeping fields.
 * A project created manually (Manual §2.1) has [sourceXmlVersion] = null and empty BOQ/schedule.
 */
@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val code: String,
    val name: String,
    val location: String?,
    val client: String?,
    val startDate: String?,       // ISO-8601 date (YYYY-MM-DD), stored as text — see DECISIONS.md
    val plannedFinish: String?,   // ISO-8601 date
    val reportEmail: String?,
    val sourceXmlVersion: String?,  // e.g. "1.0" from <zcesProject version="..."> when imported; null if created manually
    val archived: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long,
)
