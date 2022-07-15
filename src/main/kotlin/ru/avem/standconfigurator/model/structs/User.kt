package ru.avem.standconfigurator.model.structs

@kotlinx.serialization.Serializable
data class User(
    val name: String = "",
    val login: String = "",
    val password: String = ""
)
