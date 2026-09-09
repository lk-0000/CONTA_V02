package com.zces.conta.data.repository

import com.zces.conta.data.local.dao.ProjectDao
import com.zces.conta.data.local.entity.ProjectEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * Manual project creation/query (Manual §2.1, Roadmap phase 3). XML import and full-project
 * ZIP restore get their own repositories in later phases (Roadmap phases 7 and 11) since they
 * involve BOQ/schedule/entry/photo transactions, not just a ProjectEntity row.
 */
class ProjectRepository(private val projectDao: ProjectDao) {

    fun observeActiveProjects(): Flow<List<ProjectEntity>> = projectDao.observeActiveProjects()

    fun observeArchivedProjects(): Flow<List<ProjectEntity>> = projectDao.observeArchivedProjects()

    fun observeProject(projectId: String): Flow<ProjectEntity?> = projectDao.observeById(projectId)

    suspend fun createManualProject(
        code: String,
        name: String,
        location: String?,
        client: String?,
        startDate: String?,
        plannedFinish: String?,
    ): ProjectEntity {
        val now = System.currentTimeMillis()
        val project = ProjectEntity(
            id = UUID.randomUUID().toString(),
            code = code,
            name = name,
            location = location,
            client = client,
            startDate = startDate,
            plannedFinish = plannedFinish,
            reportEmail = null,
            sourceXmlVersion = null,
            createdAt = now,
            updatedAt = now,
        )
        projectDao.insert(project)
        return project
    }

    suspend fun archive(project: ProjectEntity, archived: Boolean = true) {
        projectDao.setArchived(project.id, archived, System.currentTimeMillis())
    }

    suspend fun delete(project: ProjectEntity) = projectDao.delete(project)
}
