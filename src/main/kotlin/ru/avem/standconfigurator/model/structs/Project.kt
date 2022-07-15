package ru.avem.standconfigurator.model.structs

@kotlinx.serialization.Serializable
data class Project(
    var name: String = "",
    var type: ProjectClass = ProjectClass.Custom,
    var createDate: String = "",
    var modificationDate: String = "",
    var author: String = "",
    val tests: List<Test> = type.initialTests,
    val devices: MutableList<Device> = mutableListOf()
) {
    override fun toString() =
        "Название: $name | Тип: $type | Дата изменения: $modificationDate | Дата создания: $createDate | Автор: $author"
}
