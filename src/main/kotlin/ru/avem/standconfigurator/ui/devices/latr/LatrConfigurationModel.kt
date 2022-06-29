package ru.avem.standconfigurator.ui.devices.latr

class LatrConfigurationModel(
    val count: Int,
    val transformationCoefficient: Float,
    val lowVoltageCoefficient: Float,
    val isCanManualRegulate: Boolean,
    val isRegulateByDevice: Boolean,
    val deviceIdForRegulateBy: String,
    val rotationSpeed: Float,
    val wantedVoltagePerSecond: Int,
    val regulationTimeout: Int,
)
