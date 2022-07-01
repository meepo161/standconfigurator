package ru.avem.standconfigurator.model.blob

@kotlinx.serialization.Serializable
data class User(
    val name: String = "",
    val login: String = "",
    val password: String = ""
)
