package ru.avem.standconfigurator.model.structs

@kotlinx.serialization.Serializable
sealed class ProjectType(var name: String, val initialTests: List<Test>, val initialDevices: List<Device>) {
    object KSPEM : ProjectType("КСПЭМ", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf<String>().map(::Device))
    object KSPAD : ProjectType("КСПАД", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf<String>().map(::Device))
    object KSPT : ProjectType("КСПТ", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf<String>().map(::Device))
    object VIU : ProjectType("ВИУ", listOf("ИКАС", "Меггер", "ВИУ").map(::Test), listOf<String>().map(::Device))
    object LIVS : ProjectType("ЛИВС", listOf("ИКАС", "Меггер", "ВИУ").map(::Test), listOf<String>().map(::Device))
    object KSIN : ProjectType("КСИН", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf<String>().map(::Device))
    object SUM : ProjectType("SUM", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf<String>().map(::Device))
    object Custom : ProjectType("Другой", listOf(), listOf())

    override fun toString() = name
}

val projectTypes = listOf(
    ProjectType.KSPEM,
    ProjectType.KSPAD,
    ProjectType.KSPT,
    ProjectType.VIU,
    ProjectType.LIVS,
    ProjectType.Custom
)
