package ru.avem.standconfigurator.view.devices

import androidx.compose.runtime.Composable
import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.view.devices.latr.LatrConfigurator

@Composable
fun DeviceConfiguratorWidget(device: Device) { // TODO Удалить, когда латрКонфигуратор будет приведёт к общему виду
    when (device.name) {
        "АВЭМ АТР" -> LatrConfigurator()
        else -> DeviceConfigurator(device)
    }
}
