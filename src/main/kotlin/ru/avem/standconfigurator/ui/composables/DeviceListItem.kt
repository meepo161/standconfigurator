package ru.avem.standconfigurator.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.avem.standconfigurator.model.devices.Device

@Composable
fun DeviceListItem(device: Device, selectedDevice: Device?, onClick: (Device) -> Unit) {
    TextButton(
        modifier = Modifier.background(if (selectedDevice == device) Color.Cyan else Color.White).fillMaxWidth(),
        onClick = {
            onClick(device)
        }) {
        Text(
            modifier = Modifier
                .background(if (selectedDevice == device) Color.Cyan else Color.White),
            text = device.type
        )
    }
}