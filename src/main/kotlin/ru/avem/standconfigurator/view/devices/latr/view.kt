package ru.avem.standconfigurator.view.devices.latr

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.avem.standconfigurator.formatRealNumber
import ru.avem.standconfigurator.view.widgets.ComboBox

@Composable
fun LatrConfigurator(configurationModel: LatrConfigurationViewModel = LatrConfigurationViewModel(
    model = LatrConfigurationModel(
        count = 1,
        transformationCoefficient = 1f,
        lowVoltageCoefficient = 1f,
        isCanManualRegulate = false,
        isRegulateByDevice = false,
        deviceIdForRegulateBy = "",
        wantedVoltagePerSecond = 2000,
        rotationSpeed = 1f,
        regulationTimeout = 40
    )
)) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Количество катушек")
            TextField(
                value = configurationModel.count.value,
                onValueChange = { text: String ->
                    configurationModel.count.value = if (text.isEmpty()) {
                        ""
                    } else {
                        text.toUByteOrNull()?.toString() ?: configurationModel.count.value
                    }
                },
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Коэффициент трансформации")
            TextField(
                value = configurationModel.transformationCoefficient.value,
                onValueChange = { text: String ->
                    if (text.length >= 8) return@TextField
                    configurationModel.transformationCoefficient.value = if (text.isEmpty()) {
                        ""
                    } else {
                        formatRealNumber(
                            text.toDoubleOrNull() ?: configurationModel.transformationCoefficient.value.toDouble()
                        ).toString()
                    }
                },
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Коэффициент трансформации отвода")
            TextField(
                value = configurationModel.lowVoltageCoefficient.value,
                onValueChange = { text: String ->
                    if (text.length >= 8) return@TextField
                    configurationModel.lowVoltageCoefficient.value = if (text.isEmpty()) {
                        ""
                    } else {
                        formatRealNumber(
                            text.toDoubleOrNull() ?: configurationModel.lowVoltageCoefficient.value.toDouble()
                        ).toString()
                    }
                },
                maxLines = 1,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().height(64.dp).clickable {
                configurationModel.isCanManualRegulate.value = !configurationModel.isCanManualRegulate.value
            }) {
            Text("Ручная регулировка")
            Checkbox(
                modifier = Modifier.padding(end = 4.dp),
                checked = configurationModel.isCanManualRegulate.value,
                onCheckedChange = null
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().height(64.dp).clickable {
                configurationModel.isRegulateByDevice.value = !configurationModel.isRegulateByDevice.value
            }) {
            Text("Регулируется по обратной связи")
            Checkbox(
                modifier = Modifier.padding(end = 4.dp),
                checked = configurationModel.isRegulateByDevice.value,
                onCheckedChange = null
            )
        }
        if (configurationModel.isRegulateByDevice.value) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Устройство по которому будет осуществляться регулировка")
                Column {
                    ComboBox(
                        initialValue = configurationModel.deviceIdForRegulateBy.value,
                        items = listOf("AVEM4(PV21)", "PARMA(PV22)", "PM130(PV23)"),
                        onDismissState = {},
                        selectedValue = {
                            configurationModel.deviceIdForRegulateBy.value = it
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Скорость вращения (об/мин.)")
            TextField(
                value = configurationModel.rotationSpeed.value,
                onValueChange = { text: String ->
                    if (text.length >= 8) return@TextField
                    configurationModel.rotationSpeed.value = if (text.isEmpty()) {
                        ""
                    } else {
                        formatRealNumber(
                            text.toDoubleOrNull() ?: configurationModel.rotationSpeed.value.toDouble()
                        ).toString()
                    }
                },
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Набор напряжения в секунду")
            TextField(
                value = configurationModel.wantedVoltagePerSecond.value,
                onValueChange = { text: String ->
                    configurationModel.wantedVoltagePerSecond.value = if (text.isEmpty()) {
                        ""
                    } else {
                        text.toIntOrNull()?.toString() ?: configurationModel.wantedVoltagePerSecond.value
                    }
                },
                maxLines = 1,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Таймаут регулировки")
            TextField(
                value = configurationModel.regulationTimeout.value,
                onValueChange = { text: String ->
                    configurationModel.regulationTimeout.value = if (text.isEmpty()) {
                        ""
                    } else {
                        text.toIntOrNull()?.toString() ?: configurationModel.regulationTimeout.value
                    }
                },
                maxLines = 1,
            )
        }
    }
}
