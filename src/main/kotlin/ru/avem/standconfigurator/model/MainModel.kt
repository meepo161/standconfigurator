package ru.avem.standconfigurator.model

import ru.avem.standconfigurator.model.blob.Test
import ru.avem.standconfigurator.model.blob.User

object MainModel {
    val testsList = listOf(Test("Опыт 1"), Test("Опыт 2"))
    lateinit var currentUser: User
    val devicesList = listOf("АВЭМ4", "ЛАТР", "ПР200", "ПР102")
}
