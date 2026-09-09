package com.zces.conta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zces.conta.data.repository.AuthorRepository
import com.zces.conta.data.repository.ProjectRepository
import com.zces.conta.ui.common.SimpleViewModelFactory
import com.zces.conta.ui.onboarding.AuthorNameScreen
import com.zces.conta.ui.onboarding.OnboardingViewModel
import com.zces.conta.ui.projects.AddProjectManualDialog
import com.zces.conta.ui.projects.ProjectsListScreen
import com.zces.conta.ui.projects.ProjectsViewModel

private object Routes {
    const val ONBOARDING = "onboarding"
    const val PROJECTS = "projects"
}

@Composable
fun ZCesNavHost(
    authorRepository: AuthorRepository,
    projectRepository: ProjectRepository,
) {
    val navController = rememberNavController()
    val isOnboarded by produceState<Boolean?>(initialValue = null) {
        authorRepository.isOnboarded.collect { value = it }
    }

    // Wait for the first DataStore read before deciding the start destination, otherwise
    // an onboarded user would briefly flash the name prompt on every cold start.
    if (isOnboarded == null) return

    NavHost(
        navController = navController,
        startDestination = if (isOnboarded == true) Routes.PROJECTS else Routes.ONBOARDING,
    ) {
        composable(Routes.ONBOARDING) {
            val vm: OnboardingViewModel = viewModel(factory = SimpleViewModelFactory { OnboardingViewModel(authorRepository) })
            AuthorNameScreen(
                onSubmit = { name ->
                    vm.submitAuthorName(name) {
                        navController.navigate(Routes.PROJECTS) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true }
                        }
                    }
                },
            )
        }
        composable(Routes.PROJECTS) {
            val vm: ProjectsViewModel = viewModel(factory = SimpleViewModelFactory { ProjectsViewModel(projectRepository) })
            var showAddDialog by remember { mutableStateOf(false) }

            ProjectsListScreen(
                viewModel = vm,
                onOpenProject = { /* Project home screen ships in roadmap phase 4+ */ },
                onAddProject = { showAddDialog = true },
            )

            if (showAddDialog) {
                AddProjectManualDialog(
                    onDismiss = { showAddDialog = false },
                    onConfirm = { code, name, location, client ->
                        vm.createManualProject(
                            code = code,
                            name = name,
                            location = location,
                            client = client,
                            startDate = null,
                            plannedFinish = null,
                            onCreated = { showAddDialog = false },
                        )
                    },
                )
            }
        }
    }
}
