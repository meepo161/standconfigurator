package ru.avem.standconfigurator.model.data

import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.model.structs.Test
import ru.avem.standconfigurator.model.structs.devices.AVEM4
import ru.avem.standconfigurator.model.structs.devices.AvemATR
import ru.avem.standconfigurator.model.structs.devices.PR

@kotlinx.serialization.Serializable
sealed class ProjectType(var name: String, val initialTests: List<Test>, val initialDevices: List<Device>) {
    object KSPEM : ProjectType(
        "КСПЭМ", listOf("1. Опыт ИКАС", "2. Опыт МГР", "3. Опыт ВИУ", "4. Опыт ХХ").map(::Test),
        listOf(
            PR(), AVEM4(), AvemATR()
        )
    )

    object KSPAD : ProjectType(
        "КСПАД",
        listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test),
        listOf(AVEM4())
    ) // TODO выбрать правильные наборы тестов и приборов для каждого шаблона

    object KSPT : ProjectType("КСПТ", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf(AVEM4()))
    object VIU : ProjectType("ВИУ", listOf("ИКАС", "Меггер", "ВИУ").map(::Test), listOf(AVEM4()))
    object LIVS : ProjectType("ЛИВС", listOf("ИКАС", "Меггер", "ВИУ").map(::Test), listOf(AVEM4()))
    object KSIN : ProjectType("КСИН", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf(AVEM4()))
    object SUM : ProjectType("SUM", listOf("ИКАС", "Меггер", "ВИУ", "ХХ").map(::Test), listOf(AVEM4()))
    object Custom : ProjectType("Другой", listOf(), listOf(AVEM4()))

    override fun toString() = name
}

val projectTypes = listOf(
    ProjectType.KSPEM,
    ProjectType.KSPAD,
    ProjectType.KSPT,
    ProjectType.VIU,
    ProjectType.LIVS,
    ProjectType.KSIN,
    ProjectType.SUM,
    ProjectType.Custom
)

fun getProjectTypeByName(name: String) = projectTypes.find { it.name == name }
