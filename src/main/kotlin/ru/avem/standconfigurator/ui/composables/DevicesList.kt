package ru.avem.standconfigurator.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.devices.Device

@Composable
fun DevicesList(modifier: Modifier = Modifier, onClick: (Device) -> Unit) {
    var selectedDevice by remember { mutableStateOf<Device?>(null) }
    LazyColumn(
        modifier = modifier,
    ) {
        repeat(MainModel.devicesList.size) {
            item {
                DeviceListItem(device = Device(MainModel.devicesList[it]), selectedDevice) {
                    onClick(it)
                    selectedDevice = it
                }
            }
        }
    }
}
