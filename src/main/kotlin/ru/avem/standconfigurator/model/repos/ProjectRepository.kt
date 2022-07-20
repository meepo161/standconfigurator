package ru.avem.standconfigurator.model.repos

import ru.avem.standconfigurator.model.data.ProjectType
import ru.avem.standconfigurator.model.structs.Project
import ru.avem.standconfigurator.model.utils.loadFromJson
import ru.avem.standconfigurator.model.utils.saveToJsonFile
import java.nio.file.Paths

object ProjectRepository {
    lateinit var projects: MutableList<Project>

    fun add(project: Project) {
        projects.add(project)

        save()
    }

    fun save() {
        saveToJsonFile(
            Paths.get("projects.json"),
            projects
        )
    }

    fun remove(project: Project) {
        projects.remove(project)

        save()
    }

    fun init() {
        try {
            projects = loadFromJson(Paths.get("projects.json"))
        } catch (e: Exception) {
            projects = mutableListOf(
                Project(
                    name = "ВолгаРемМаш",
                    projectTypeName = ProjectType.KSPEM.name,
                    createDate = "01.02.2022",
                    modificationDate = "13.07.2022",
                    author = "Чамлай В.И.",
                    tests = mutableListOf(*ProjectType.KSPEM.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.KSPEM.initialDevices.map {it.clone()}.toTypedArray()),
                ),
                Project(
                    name = "Богатырь",
                    projectTypeName = ProjectType.KSPEM.name,
                    createDate = "09.10.2021",
                    modificationDate = "13.07.2022",
                    author = "Безъязычный В.С.",
                    tests = mutableListOf(*ProjectType.KSPEM.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.KSPEM.initialDevices.map {it.clone()}.toTypedArray()),
                ),
                Project(
                    name = "SUM-1",
                    projectTypeName = ProjectType.SUM.name,
                    createDate = "08.09.2021",
                    modificationDate = "13.07.2022",
                    author = "Сулейманов М.У.",
                    tests = mutableListOf(*ProjectType.SUM.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.SUM.initialDevices.map {it.clone()}.toTypedArray()),
                ),
                Project(
                    name = "Пролетарск",
                    projectTypeName = ProjectType.KSIN.name,
                    createDate = "01.02.2021",
                    modificationDate = "01.02.2022",
                    author = "Широковских А.Н.",
                    tests = mutableListOf(*ProjectType.KSIN.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.KSIN.initialDevices.map {it.clone()}.toTypedArray()),
                ),
                Project(
                    name = "Сургут",
                    projectTypeName = ProjectType.LIVS.name,
                    createDate = "01.04.2022",
                    modificationDate = "13.07.2021",
                    author = "Вайсберг Ю.В.",
                    tests = mutableListOf(*ProjectType.LIVS.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.LIVS.initialDevices.map {it.clone()}.toTypedArray()),
                ),
                Project(
                    name = "ИркутскКСПЭМ",
                    projectTypeName = ProjectType.KSPEM.name,
                    createDate = "02.04.2022",
                    modificationDate = "14.07.2021",
                    author = "Магомедова Д.У.",
                    tests = mutableListOf(*ProjectType.KSPEM.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.KSPEM.initialDevices.map {it.clone()}.toTypedArray()),
                ),
                Project(
                    name = "ЭТЗ",
                    projectTypeName = ProjectType.KSPT.name,
                    createDate = "05.06.2021",
                    modificationDate = "08.08.2021",
                    author = "Климахин В.С.",
                    tests = mutableListOf(*ProjectType.KSPT.initialTests.map {it.copy()}.toTypedArray()),
                    devices = mutableListOf(*ProjectType.KSPT.initialDevices.map {it.clone()}.toTypedArray()),
                ),
            )
            saveToJsonFile(
                Paths.get("projects.json"),
                projects
            )
        }
    }
}
