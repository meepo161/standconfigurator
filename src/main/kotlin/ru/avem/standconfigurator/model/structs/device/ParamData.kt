package ru.avem.standconfigurator.model.structs.device

@kotlinx.serialization.Serializable
data class ParamData(
    val name: String= "",
    val unit: String= "",
    val fieldType: FieldType = FieldType.NONE,
    val isIndicate: Boolean = false,
    val stores: MutableList<String> = mutableListOf()
)