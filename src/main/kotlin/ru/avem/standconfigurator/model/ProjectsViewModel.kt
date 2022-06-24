package ru.avem.standconfigurator.model

import androidx.compose.runtime.mutableStateListOf
import ru.avem.standconfigurator.model.blob.Project

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
