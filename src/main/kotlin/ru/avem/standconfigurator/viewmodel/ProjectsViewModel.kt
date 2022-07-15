package ru.avem.standconfigurator.viewmodel

import androidx.compose.runtime.mutableStateListOf
import ru.avem.standconfigurator.model.repos.ProjectRepository
import ru.avem.standconfigurator.model.structs.Project

class ProjectsViewModel {
    var projects = mutableStateListOf(*ProjectRepository.projects.toTypedArray())

    fun add(project: Project) {
        ProjectRepository.add(project)
        projects.add(project)
    }

    fun remove(project: Project) {
        ProjectRepository.remove(project)
        projects.remove(project)
    }

    fun getFilteredProjects(predicate: String): List<Project> = projects.filter { it.name.contains(predicate) }
}
