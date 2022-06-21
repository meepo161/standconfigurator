package ru.avem.standconfigurator.model

import ru.avem.standconfigurator.model.blob.Project
import ru.avem.standconfigurator.model.utils.loadFromJson
import ru.avem.standconfigurator.model.utils.saveToJsonFile
import java.nio.file.Paths

object ProjectRepository {
    lateinit var projects: MutableList<Project>

    fun add(project: Project) {
        projects.add(project)

        saveToJsonFile(
            Paths.get("projects.json"),
            projects
        )
    }

    fun remove(project: Project) {
        projects.remove(project)

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
                Project("КСПЭМ", "01.01.2021", "Магомедова Д.У."),
                Project("КСПАД", "02.01.2021", "Магомедова Д.У."),
                Project("КСПТ", "03.01.2021", "Климахин В.С."),
                Project("СНИМ", "04.01.2021", "Широковских А.А."),
                Project("SUM-1", "05.01.2021", "Сулейманов М.У."),
                Project("ЛЭИС", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС1", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС2", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС3", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС4", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС5", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС6", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС7", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС8", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС9", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС10", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС11", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС12", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС13", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС14", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС15", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС16", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС17", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС18", "06.01.2021", "Вайсберг Ю.В."),
                Project("ЛЭИС19", "06.01.2021", "Вайсберг Ю.В."),
                Project("50", "06.01.2021", "Вайсберг Ю.В."),
            )
            saveToJsonFile(
                Paths.get("projects.json"),
                projects
            )
        }
    }
}
