package ru.avem.standconfigurator.model.blob

import ru.avem.standconfigurator.model.IListItem

@kotlinx.serialization.Serializable
data class Device(override val text: String) : IListItem

// добавить поля в девайс TODO