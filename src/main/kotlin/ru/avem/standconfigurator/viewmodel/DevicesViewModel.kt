package ru.avem.standconfigurator.viewmodel

import androidx.compose.runtime.mutableStateListOf
import ru.avem.standconfigurator.model.structs.Device

class DevicesViewModel(private val devices: MutableList<Device>) {
    var stateDevices = mutableStateListOf(*devices.toTypedArray())
        private set

    fun add(device: Device) {
        devices.add(device)
        stateDevices.add(device)
    }

    fun remove(device: Device) {
        devices.remove(device)
        stateDevices.remove(device)
    }

    fun removeAt(deviceIdx: Int) {
        devices.removeAt(deviceIdx)
        stateDevices.removeAt(deviceIdx)
    }
}
