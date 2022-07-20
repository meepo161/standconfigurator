package ru.avem.standconfigurator.model.structs


@kotlinx.serialization.Serializable
data class Test(
    val name: String,
    val logics: MutableList<LogicItem> = mutableListOf()
) {
    override fun toString() = name
}
