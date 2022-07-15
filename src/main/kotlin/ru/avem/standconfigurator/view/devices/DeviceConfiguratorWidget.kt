package ru.avem.standconfigurator.view.devices

import androidx.compose.runtime.Composable
import ru.avem.standconfigurator.view.devices.avem4.AVEM4Configurator
import ru.avem.standconfigurator.view.devices.latr.LatrConfigurator

@Composable
fun DeviceConfiguratorWidget(name: String) {
    when (name) {
        "АВЭМ АТР" -> LatrConfigurator()
        "АВЭМ-4" -> AVEM4Configurator()
        else -> error("Нет такого прибора в БД")
    }
}
