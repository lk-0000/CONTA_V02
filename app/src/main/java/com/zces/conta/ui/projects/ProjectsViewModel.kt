package com.zces.conta.ui.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zces.conta.data.local.entity.ProjectEntity
import com.zces.conta.data.repository.ProjectRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProjectsViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    val activeProjects: StateFlow<List<ProjectEntity>> =
        projectRepository.observeActiveProjects()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    /**
     * Manual project creation (Manual §2.1). XML/ZIP import get their own flows in later
     * roadmap phases (7 and 11) since they involve BOQ/schedule/photo transactions.
     */
    fun createManualProject(
        code: String,
        name: String,
        location: String?,
        client: String?,
        startDate: String?,
        plannedFinish: String?,
        onCreated: (ProjectEntity) -> Unit,
    ) {
        if (code.isBlank() || name.isBlank()) return
        viewModelScope.launch {
            val project = projectRepository.createManualProject(
                code = code.trim(),
                name = name.trim(),
                location = location?.trim()?.ifBlank { null },
                client = client?.trim()?.ifBlank { null },
                startDate = startDate,
                plannedFinish = plannedFinish,
            )
            onCreated(project)
        }
    }
}
