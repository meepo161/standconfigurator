package ru.avem.standconfigurator.model

import androidx.compose.runtime.MutableState
import ru.avem.standconfigurator.model.structs.User
import ru.avem.standconfigurator.model.structs.devices.AVEM4
import ru.avem.standconfigurator.model.structs.devices.AvemATR
import ru.avem.standconfigurator.model.structs.devices.PR

object MainModel {
    lateinit var isOpen: MutableState<Boolean>

    lateinit var currentUser: User

    val allDevices = mapOf(
        "АВЭМ АТР" to AvemATR(),
        "АВЭМ-4" to AVEM4(),
        "ПР" to PR(),
    )
}
