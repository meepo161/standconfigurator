package ru.avem.standconfigurator.model

import androidx.compose.runtime.mutableStateListOf
import ru.avem.standconfigurator.model.blob.Device
import ru.avem.standconfigurator.model.blob.Project

class DevicesViewModel(private val _devices: MutableList<Device>) {
    var devices = mutableStateListOf(*_devices.toTypedArray())

    fun add(devices: Device) {
        _devices.add(devices)
        this.devices.add(devices)
    }

    fun remove(project: Device) {
        _devices.remove(project)
        devices.remove(project)
    }
}
