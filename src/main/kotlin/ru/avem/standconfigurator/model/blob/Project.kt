package ru.avem.standconfigurator.model.blob

import kotlinx.serialization.Serializable
@Serializable
data class Project(
    var name: String = "",
    var date: String = "",
    var author: String = ""
) {
    override fun toString() = "$name | $date | $author"
}
