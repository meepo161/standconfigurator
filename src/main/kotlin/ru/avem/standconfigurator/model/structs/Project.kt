package ru.avem.standconfigurator.model.structs

import ru.avem.standconfigurator.model.structs.device.Device

@kotlinx.serialization.Serializable
data class Project(
    var name: String = "",
    var projectTypeName: String = "",
    var createDate: String = "",
    var modificationDate: String = "",
    var author: String = "",
    val tests: MutableList<Test> = mutableListOf(),
    val devices: MutableList<Device> = mutableListOf()
) {
    override fun toString() =
        "Название: $name | Тип: $projectTypeName | Дата изменения: $modificationDate | Дата создания: $createDate | Автор: $author"
}
