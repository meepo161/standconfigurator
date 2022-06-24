package ru.avem.standconfigurator.model

import ru.avem.standconfigurator.model.blob.User

object MainModel {
    lateinit var currentUser: User
    val devicesList = listOf("АВЭМ4", "ЛАТР", "ПР200", "ПР102")
}
