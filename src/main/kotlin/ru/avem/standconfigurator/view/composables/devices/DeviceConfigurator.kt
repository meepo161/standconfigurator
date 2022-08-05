package ru.avem.standconfigurator.view.composables.devices

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.avem.standconfigurator.model.structs.device.Device
import ru.avem.standconfigurator.model.structs.device.FieldType
import ru.avem.standconfigurator.view.composables.ComboBox

@Composable
fun DeviceConfigurator(device: Device) {
    Column(modifier = Modifier.padding(16.dp)) {
        device.params.forEach { param ->
            val paramData = param.paramData
            val paramValue = param.paramValue
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    if (paramData.unit.isNotEmpty()) {
                        "${paramData.name}, ${paramData.unit}"
                    } else {
                        "${paramData.name} "
                    }
                )
                when (paramData.fieldType) {
                    FieldType.STRING -> TextField(
                        value = paramValue.storeState.value,
                        onValueChange = {
                            paramValue.changeValue(it)
                        },
                        maxLines = 1,
                    )
                    FieldType.FLOAT -> TextField(
                        value = paramValue.storeState.value,
                        onValueChange = {
                            if (it.toFloatOrNull() != null) {
                                paramValue.changeValue(it)
                            }
                        },
                        maxLines = 1,
                    )
                    FieldType.DOUBLE -> TextField(
                        value = paramValue.storeState.value,
                        onValueChange = {
                            if (it.toDoubleOrNull() != null) {
                                paramValue.changeValue(it)
                            }
                        },
                        maxLines = 1,
                    )
                    FieldType.INT -> TextField(
                        value = paramValue.storeState.value,
                        onValueChange = {
                            if (it.toIntOrNull() != null) {
                                paramValue.changeValue(it.toInt().toString())
                            }
                        },
                        maxLines = 1,
                    )
                    FieldType.BOOL -> Switch(
                        checked = paramValue.storeState.value.toBoolean(),
                        onCheckedChange = {
                            paramValue.changeValue(it.toString())
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            checkedTrackColor = MaterialTheme.colors.primary
                        )
                    )
                    FieldType.ENUM -> ComboBox(
                            selectedItem = paramValue.storeState,
                            items = paramData.stores,
                            selectedValue = {
                                paramValue.changeValue(it)
                            }
                        )
                    FieldType.NONE -> error("Type.NONE")
                }
            }
        }
    }
}
