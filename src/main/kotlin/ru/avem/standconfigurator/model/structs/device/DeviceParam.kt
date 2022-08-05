package ru.avem.standconfigurator.model.structs.device

@kotlinx.serialization.Serializable
data class DeviceParam(
    val paramData: ParamData,
    val paramValue: ParamValue
)
