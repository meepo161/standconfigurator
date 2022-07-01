package ru.avem.standconfigurator.model

import androidx.compose.runtime.mutableStateOf
import ru.avem.standconfigurator.model.blob.Device
import ru.avem.standconfigurator.model.blob.Project
import ru.avem.standconfigurator.model.blob.User

object MainModel {
    var isOpen = mutableStateOf(true)

    lateinit var currentUser: User

    lateinit var currentProject: Project

    val allDevices = listOf("АВЭМ4", "ЛАТР", "ПР200", "ПР102").map(::Device).toMutableList()
}
