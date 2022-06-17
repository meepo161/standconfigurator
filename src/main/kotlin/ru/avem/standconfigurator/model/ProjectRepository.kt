package ru.avem.standconfigurator.model

import java.nio.file.Paths

object ProjectRepository {
    lateinit var projects: MutableList<ProjectModel>

    fun addUser(projectModel: ProjectModel) {
        projects.add(projectModel)

        saveToJsonFile(
            Paths.get("users.json"),
            projects
        )
    }

    fun init() {
        try {
            projects = loadFromJson(Paths.get("projects.json"))
        } catch (e: Exception) {
            projects = mutableListOf(
                ProjectModel("КСПЭМ", "01.01.2021", "Магомедова Д.У."),
                ProjectModel("КСПАД", "02.01.2021", "Магомедова Д.У."),
                ProjectModel("КСПТ", "03.01.2021", "Климахин В.С."),
                ProjectModel("СНИМ", "04.01.2021", "Широковских А.А."),
                ProjectModel("SUM-1", "05.01.2021", "Сулейманов М.У."),
                ProjectModel("ЛЭИС", "06.01.2021", "Вайсберг Ю.В."),
            )
            saveToJsonFile(
                Paths.get("projects.json"),
                projects
            )
        }
    }
}
