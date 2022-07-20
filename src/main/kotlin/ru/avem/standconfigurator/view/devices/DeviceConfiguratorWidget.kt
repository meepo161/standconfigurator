package ru.avem.standconfigurator.view.devices

import androidx.compose.runtime.Composable
import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.view.devices.latr.LatrConfigurator

@Composable
fun DeviceConfiguratorWidget(device: Device) {
    when (device.name) {
        "АВЭМ АТР" -> LatrConfigurator()
        "АВЭМ-4" -> DeviceConfigurator(device)
        else -> error("Нет такого прибора в БД")
    }
}
