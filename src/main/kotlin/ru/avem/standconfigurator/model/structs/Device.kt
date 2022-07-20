package ru.avem.standconfigurator.model.structs
import androidx.compose.runtime.mutableStateOf

@kotlinx.serialization.Serializable
open class Device(
    var mark: String = "",
    var address: Int = 0,
    var name: String,
    var params: List<Pair<ParamData, Value>> = listOf(),
) {
    fun clone(): Device {
        return Device(
            mark = mark,
            address = address,
            name = name,
            params = params.mapIndexed { i, it ->
                it.first.copy() to it.second.clone(params[i].second)
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
data class Value(var value: String = "", val values: List<String> = listOf()) {
    @kotlinx.serialization.Transient var valueState = mutableStateOf(value)

    fun changeValue(_value: String) {
        value = _value
        valueState.value = _value
    }

    fun clone(old: Value) = Value(
        old.value,
        old.values.map { it }
    ).apply {
        valueState = mutableStateOf(old.valueState.value)
    }
}
