package ru.avem.standconfigurator.model

import java.nio.file.Paths

object ProjectRepository {
    lateinit var projects: MutableList<ProjectModel>

    fun addProject(projectModel: ProjectModel) {
        projects.add(projectModel)

        saveToJsonFile(
            Paths.get("projects.json"),
            projects
        )
    }

    fun removeProject(projectIndex: Int) {
        projects.removeAt(projectIndex)

        saveToJsonFile(
            Paths.get("projects.json"),
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
                ProjectModel("ЛЭИС1", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС2", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС3", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС4", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС5", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС6", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС7", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС8", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС9", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС10", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС11", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС12", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС13", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС14", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС15", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС16", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС17", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС18", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("ЛЭИС19", "06.01.2021", "Вайсберг Ю.В."),
                ProjectModel("50", "06.01.2021", "Вайсберг Ю.В."),
            )
            saveToJsonFile(
                Paths.get("projects.json"),
                projects
            )
        }
    }
}
