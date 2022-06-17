package ru.avem.standconfigurator.model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectModel(
    var name: String = "",
    var date: String = "",
    var author: String = ""
) {
    override fun toString() = "$name | $date | $author"
}

