package ru.avem.standconfigurator.model

import kotlinx.serialization.Serializable

@Serializable
data class ProjectModel(
    val name: String = "",
    val date: String = "",
    val author: String = ""
)

