package ru.avem.standconfigurator.ui.devices.latr

import androidx.compose.runtime.mutableStateOf

class LatrConfigurationViewModel(model: LatrConfigurationModel) {
    var count = mutableStateOf(model.count.toString())
    var transformationCoefficient = mutableStateOf(model.transformationCoefficient.toString())
    var lowVoltageCoefficient = mutableStateOf(model.lowVoltageCoefficient.toString())
    var isCanManualRegulate = mutableStateOf(model.isCanManualRegulate)
    var isRegulateByDevice = mutableStateOf(model.isRegulateByDevice)
    var deviceIdForRegulateBy = mutableStateOf(model.deviceIdForRegulateBy)
    var rotationSpeed = mutableStateOf(model.rotationSpeed.toString())
    var wantedVoltagePerSecond = mutableStateOf(model.wantedVoltagePerSecond.toString())
    var regulationTimeout = mutableStateOf(model.regulationTimeout.toString())

    @Suppress("unused")
    fun buildModel(): LatrConfigurationModel {
        return LatrConfigurationModel(
            count = count.value.toInt(),
            transformationCoefficient = transformationCoefficient.value.toFloat(),
            lowVoltageCoefficient = lowVoltageCoefficient.value.toFloat(),
            isCanManualRegulate = isCanManualRegulate.value,
            isRegulateByDevice = isRegulateByDevice.value,
            deviceIdForRegulateBy = deviceIdForRegulateBy.value,
            rotationSpeed = rotationSpeed.value.toFloat(),
            wantedVoltagePerSecond = wantedVoltagePerSecond.value.toInt(),
            regulationTimeout = regulationTimeout.value.toInt()
        )
    }
}
