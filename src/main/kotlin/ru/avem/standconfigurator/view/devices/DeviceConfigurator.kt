package ru.avem.standconfigurator.view.devices

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.avem.standconfigurator.model.structs.Device
import ru.avem.standconfigurator.model.structs.Type
import ru.avem.standconfigurator.view.composables.ComboBox

@Composable
fun DeviceConfigurator(device: Device) {
    Column(modifier = Modifier.padding(16.dp)) {
        device.params.forEach { param ->
            val paramData = param.first
            val paramValue = param.second
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${paramData.name}, ${paramData.unit}")
                when (paramData.type) {
                    Type.FIELD_STR -> TextField(
                        value = paramValue.valueState.value,
                        onValueChange = {
                            paramValue.changeValue(it)
                        },
                        maxLines = 1,
                    )
                    Type.FIELD_FLOAT -> TextField(
                        value = paramValue.valueState.value,
                        onValueChange = {
                            if (it.toFloatOrNull() != null) {
                                paramValue.changeValue(it)
                            }
                        },
                        maxLines = 1,
                    )
                    Type.FIELD_DOUBLE -> TextField(
                        value = paramValue.valueState.value,
                        onValueChange = {
                            if (it.toDoubleOrNull() != null) {
                                paramValue.changeValue(it)
                            }
                        },
                        maxLines = 1,
                    )
                    Type.FIELD_INT -> TextField(
                        value = paramValue.valueState.value,
                        onValueChange = {
                            if (it.toIntOrNull() != null) {
                                paramValue.changeValue(it.toInt().toString())
                            }
                        },
                        maxLines = 1,
                    )
                    Type.BOOL -> Switch(
                        checked = paramValue.valueState.value.toBoolean(),
                        onCheckedChange = {
                            paramValue.changeValue(it.toString())
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary,
                            checkedTrackColor = MaterialTheme.colors.primary
                        ),

                        )
                    Type.ENUM -> ComboBox(
                        initialValue = paramValue.valueState.value,
                        items = paramValue.values,
                        selectedValue = {
                            paramValue.changeValue(it)
                        }
                    )
                    Type.NONE -> error("Type.NONE")
                    else -> error("")
                }
            }
        }
    }
}
