package ru.avem.standconfigurator.model.repos

import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.structs.ProjectClass
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
                Project(
                    name = "ВолгаРемМаш",
                    type = ProjectClass.KSPEM,
                    createDate = "01.02.2022",
                    modificationDate = "13.07.2022",
                    author = "Чамлай В.И."
                ),
                Project(
                    name = "Богатырь",
                    type = ProjectClass.KSPEM,
                    createDate = "09.10.2021",
                    modificationDate = "13.07.2022",
                    author = "Безъязычный В.С."
                ),
                Project(
                    name = "SUM-1",
                    type = ProjectClass.KSPEM,
                    createDate = "08.09.2021",
                    modificationDate = "13.07.2022",
                    author = "Сулейманов М.У."
                ),
                Project(
                    name = "Пролетарск",
                    type = ProjectClass.KSPEM,
                    createDate = "01.02.2021",
                    modificationDate = "01.02.2022",
                    author = "Широковских А.Н."
                ),
                Project(
                    name = "ПромМашТест",
                    type = ProjectClass.KSPEM,
                    createDate = "03.04.2021",
                    modificationDate = "02.07.2021",
                    author = "Магомедова Д.У."
                ),
                Project(
                    name = "Сургут",
                    type = ProjectClass.LIVS,
                    createDate = "01.04.2022",
                    modificationDate = "13.07.2021",
                    author = "Вайсберг Ю.В."
                ),
                Project(
                    name = "ЭТЗ",
                    type = ProjectClass.KSPEM,
                    createDate = "05.06.2021",
                    modificationDate = "08.08.2021",
                    author = "Климахин В.С."
                ),
            )

            saveToJsonFile(
                Paths.get("projects.json"),
                projects
            )
        }
    }
}
