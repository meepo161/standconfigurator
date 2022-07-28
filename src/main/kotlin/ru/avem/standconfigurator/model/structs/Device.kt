package ru.avem.standconfigurator.model.structs
import androidx.compose.runtime.mutableStateOf
import ru.avem.standconfigurator.PairParam

@kotlinx.serialization.Serializable
open class Device(
    var mark: String = "",
    var address: Int = 0,
    var name: String,
    var params: List<PairParam<ParamData, ParamValue>> = listOf(),
) {
    fun clone(): Device {
        return Device(
            mark = mark,
            address = address,
            name = name,
            params = params.mapIndexed { i, it ->
                PairParam(it.paramData.copy(), it.paramValue.clone(params[i].paramValue))
            }
        )
    }

    override fun toString() = "$mark$address ($name)"
}

@kotlinx.serialization.Serializable
enum class Type {
    FIELD_STR,
    FIELD_FLOAT,
    FIELD_DOUBLE,
    FIELD_INT,
    BOOL,
    ENUM,
    NONE
}

@kotlinx.serialization.Serializable
data class ParamData(
    val name: String= "",
    val unit: String= "",
    val type: Type = Type.NONE
)

@kotlinx.serialization.Serializable
data class ParamValue(var store: String = "", val stores: List<String> = listOf()) {
    @kotlinx.serialization.Transient var valueState = mutableStateOf(store)

    fun changeValue(_value: String) {
        store = _value
        valueState.value = _value
    }

    fun clone(old: ParamValue) = ParamValue(
        old.store,
        old.stores.map { it }
    ).apply {
        valueState = mutableStateOf(old.valueState.value)
    }
}
