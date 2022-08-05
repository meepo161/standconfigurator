package ru.avem.standconfigurator.model.structs.device

@kotlinx.serialization.Serializable
open class Device(
    var mark: String = "",
    var address: Int = 0,
    var name: String,
    var params: List<DeviceParam> = listOf(),
) {
    fun clone(): Device {
        return Device(
            mark = mark,
            address = address,
            name = name,
            params = params.mapIndexed { i, it ->
                DeviceParam(it.paramData, it.paramValue.clone(params[i].paramValue))
            }
        )
    }

    override fun toString() = "$mark$address ($name)"
}
