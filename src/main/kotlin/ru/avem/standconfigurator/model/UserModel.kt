package ru.avem.standconfigurator.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val name: String = "",
    val login: String = "",
    val password: String = ""
)

