package ru.avem.standconfigurator.model.structs
import androidx.compose.runtime.mutableStateOf
import ru.avem.standconfigurator.DeviceParam
import ru.avem.standconfigurator.model.ProjectModel

val properties = listOf("Коммутирующее?", "Регулирующее?", "Ус-во по которому можно регулироваться", "Ус-во по которому регулируемся")

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

@kotlinx.serialization.Serializable
enum class FieldType {
    STRING,
    FLOAT,
    DOUBLE,
    INT,
    BOOL,
    ENUM,
    NONE
}

@kotlinx.serialization.Serializable
data class ParamData(
    val name: String= "",
    val unit: String= "",
    val fieldType: FieldType = FieldType.NONE,
    val isIndicate: Boolean = false,
    val stores: MutableList<String> = mutableListOf()
)

@kotlinx.serialization.Serializable
data class ParamValue(private var store: String = "") {
    @kotlinx.serialization.Transient var storeState = mutableStateOf(store)

    fun changeValue(_value: String) {
        store = _value
        storeState.value = _value
        ProjectModel.refreshDevices() // TODO del
    }

    fun clone(old: ParamValue) = ParamValue(
        old.store,
    ).apply {
        storeState = mutableStateOf(old.storeState.value)
    }
}
