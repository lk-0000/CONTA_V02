package com.zces.conta.ui.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zces.conta.R
import com.zces.conta.data.local.entity.ProjectEntity

/**
 * PROJECT screen (Manual §2). "+ DODAJ PROJEKT" here currently only opens manual creation;
 * XML/ZIP import entry points are wired in roadmap phases 7 and 11.
 */
@Composable
fun ProjectsListScreen(
    viewModel: ProjectsViewModel,
    onOpenProject: (ProjectEntity) -> Unit,
    onAddProject: () -> Unit,
) {
    val projects by viewModel.activeProjects.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.projects_title)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddProject) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.projects_add))
            }
        },
    ) { padding ->
        if (projects.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(stringResource(R.string.projects_empty))
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize(),
            ) {
                items(projects, key = { it.id }) { project ->
                    ProjectRow(project = project, onClick = { onOpenProject(project) })
                }
            }
        }
    }
}

@Composable
private fun ProjectRow(project: ProjectEntity, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = project.name, style = MaterialTheme.typography.titleLarge)
            Text(text = project.code, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
