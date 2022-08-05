package ru.avem.standconfigurator.model.structs.device

import androidx.compose.runtime.mutableStateOf
import ru.avem.standconfigurator.model.ProjectModel

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