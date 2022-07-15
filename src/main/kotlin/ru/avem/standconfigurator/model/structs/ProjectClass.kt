package ru.avem.standconfigurator.model.structs

@kotlinx.serialization.Serializable
open class ProjectClass(val name: String, val initialTests: List<Test>, val initialDevices: List<String>) {
    object Custom : ProjectClass("Другой", listOf(), listOf())
    object KSPEM : ProjectClass("КСПЭМ", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf())
    object KSPAD : ProjectClass("КСПАД", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf())
    object KSPT : ProjectClass("КСПТ", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf())
    object VIU : ProjectClass("ВИУ", listOf("ИКАС", "Меггер", "ВИУ").map(::Test), listOf())
    object LIVS : ProjectClass("ЛИВС", listOf("ИКАС", "Меггер", "ВИУ").map(::Test), listOf())

    override fun toString() = name
}


val projectClasses = listOf(
    ProjectClass.Custom, ProjectClass.KSPEM, ProjectClass.KSPAD,
    ProjectClass.KSPT, ProjectClass.VIU, ProjectClass.LIVS
)
