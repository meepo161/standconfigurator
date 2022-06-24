package ru.avem.standconfigurator.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.avem.standconfigurator.model.MainModel
import ru.avem.standconfigurator.model.devices.Device
import ru.avem.standconfigurator.view.widgets.DeviceListItem

@Composable
fun Devices(modifier: Modifier = Modifier, onClick: (Device) -> Unit) {
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
