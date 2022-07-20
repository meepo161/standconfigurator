package ru.avem.standconfigurator.viewmodel

import androidx.compose.runtime.mutableStateListOf
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.structs.Device

class DevicesViewModel(private val devices: MutableList<Device>) {
    var stateDevices = mutableStateListOf(*devices.toTypedArray())
        private set

    fun add(device: Device) {
        devices.add(device)
        stateDevices.add(device)
    }

    fun add(deviceName: String) {
        val baseDevice = MainModel.allDevices[deviceName] ?: error("Handle")
        val needMarkDevices = stateDevices.filter { it.mark == baseDevice.mark }
        var address = needMarkDevices.maxOfOrNull { it.address }
        if (address != null) {
            address += 1
            //todo обработать address > 251
            baseDevice.address = minOf(address, 251)
        }
        val newDevice = baseDevice.clone()
        devices.add(newDevice)
        stateDevices.add(newDevice)
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
