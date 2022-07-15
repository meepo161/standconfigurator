package ru.avem.standconfigurator.model.structs

import ru.avem.standconfigurator.model.IListItem

@kotlinx.serialization.Serializable
data class Test(
    override val text: String,
    val logics: MutableList<LogicItem> = mutableListOf()
) : IListItem
